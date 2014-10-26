
package org.easyweb.client

/**
 * this is for http operation...
 */
class HttpBuilder {


	def header = [:]
	def path = ""
	def get = false
	def json = false

	def get() {
		this.get = true
		return this
	}

    def post() {
        this.get = false
        return this
    }
	def json() {
		this.json = true
		return this
	}

	def head(def key, def value) {
		this.header[key] = value
		return this
	}

	def path(def path) {
		this.path = path
		return this
	}


	def create() {
		if(path == null || path.trim().equals("")) throw new IllegalArgumentException("path should not be null or blank")

		if(get) {
			return new GetHttpAction()
		}else {

			//we are transport json data ....
			if(json) {
				return new JsonHttpAction()
			}else {
				return new PostHttpAction()
			}
		}
	}


	interface HttpAction {
		def getResponseCode()
        def getErrorMsg()
		def getResponseText()
	}

	/**
	* the get http action
	**/
	class GetHttpAction implements HttpAction {

		HttpURLConnection con
		
		def GetHttpAction() {
			//do some init...
			init();
		}

		def init() {

			header.each {K, V -> 
				header[URLEncoder.encode(K,"utf-8")] = URLEncoder.encode(v,"utf-8")
			}

			path = path + "?"

			header.each { K, V -> 
				path = K + "=" + V +"&"
			}

			def url = new URL(path)
			con = (HttpURLConnection)url.openConnection()
			con.setDoOutput(true)
			con.setRequestMethod("GET")
		}

		
		@Override
		def getResponseCode() {
			return con.getResponseCode()
		}

        @Override
        def getErrorMsg() {
            if(getResponseCode() == 500)
                return con.getResponseMessage()
        }

        @Override
		def getResponseText() {
			def builder = new StringBuilder()
			con.getInputStream().eachLine {
				builder.append(it)
			}
			con.getInputStream().close()
			return builder.toString()
		}
	}

	/**
	* the post http action
	**/

	class PostHttpAction implements HttpAction {
	

		def con

		def PostHttpAction() {
			init()
		}

		def init() {
			
			def data = encode(header).getBytes("utf-8")
			
			def url = new URL(path)

			con = (HttpURLConnection)url.openConnection()
			con.setDoInput(true)
			con.setDoOutput(true)
			
			con.setRequestProperty("content-type", contentType())
			con.setRequestProperty("content-Length", String.valueOf(data.length))
			
			def out = con.getOutputStream()

			out.write(data);
			out.flush()
			out.close()
		}

		def contentType() {
			return "application/x-www-form-urlencoded"		
		}

		def encode(def header) {
			def builder = new StringBuilder()

			header.each { K, V ->
				if(builder.length() != 0) builder.append("&")
					builder.append(URLEncoder.encode(K,"utf-8"))
					builder.append("=")
					builder.append(URLEncoder.encode(V,"utf-8"))
			}

			return builder.toString()

		}

		@Override
		def getResponseCode() {
			return con.getResponseCode()
		}

        @Override
        def getErrorMsg() {
            if(getResponseCode() == 500)
                return con.getResponseMessage()
        }

		@Override
		def getResponseText() {
			def builder = new StringBuilder()
			con.getInputStream().eachLine {
				builder.append(it)
			}
			con.getInputStream().close()
			return builder.toString()
		}
	}


	class JsonHttpAction extends PostHttpAction {
		
		def JsonHttpAction() {
			super()
		}

		@Override
		def encode(def header) {
			return header["json"]
		}

		@Override
		def contentType() {
			return "application/json"
		}
	}
}
