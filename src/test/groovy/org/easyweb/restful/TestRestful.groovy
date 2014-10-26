import org.easyweb.client.HttpBuilder
import org.junit.Test
import org.easyweb.restful.BaseRestful

class TestRestful extends BaseRestful {

	@Test
	public void index() {
        latch.await()
		def action = new HttpBuilder()
			.path("http://localhost:8089/rest/test/index")
			.get()
			.create()

		printf "code = %d\n",action.responseCode
		printf "msg = %s\n",action.responseText
	}

    @Test
    public void login() {
        latch.await()

        def json =  '''
            {
                "name":"angelgreen",
                "password":"333"
            }
        '''
        def action = new HttpBuilder()
        .path("http://localhost:8089/rest/test/login")
        .json()
        .head("json",json)
        .create()

        printf "%d\n", action.responseCode
        printf "%s\n", action.responseText
    }

    @Test
    public void json() {
        def json = '''
            {
                "name":"angelgreen",
                "password":"1234"
            }
        '''

        latch.await()

        def action = new HttpBuilder()
        .path("http://localhost:8089/rest/test/json")
        .json()
        .head("json",json)
        .create()

        printf "code = %d\n",action.responseCode
        printf "msg = %s\n",action.responseText
    }

    @Test
    public void form() {
        latch.await()

        def action = new HttpBuilder()
        .path("http://localhost:8089/rest/test/form")
        .post()
        .head("name","angelgreen")
        .head("password","12345")
        .create()

        printf "code = %d\n", action.responseCode
        printf "msg = %s\n", action.responseText
    }

    @Test
    public void saveUser() {

        latch.await()

        def json = '''
            {
                "name":"angelking",
                "password":"1234"
            }
        '''
        def action = new HttpBuilder()
         .path("http://localhost:8089/rest/test/saveUser")
        .json()
        .head("json",json)
        .create()

        printf "%d\n" , action.responseCode
        printf "%s\n" , action.responseText
    }

    @Test
    public void page() {
        latch.await()

        def action = new HttpBuilder()
                .path("http://localhost:8089/rest/test/page")
                .get()
                .create()

        printf "%d\n" , action.responseCode
        printf '%s\n' , action.errorMsg
//        printf "%s\n" , action.responseText
    }
}
