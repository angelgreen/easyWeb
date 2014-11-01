import groovy.json.JsonSlurper
import org.easyweb.client.HttpBuilder
import org.junit.Test
import org.easyweb.restful.BaseRestful

class TestRestful extends BaseRestful {

	@Test
	public void test() {

        latch.await()
		def action = new HttpBuilder()
			.path("http://localhost:8089/rest/error/test")
			.get()
			.create()

		printf "code = %d\n",action.responseCode
		printf "msg = %s\n",action.responseText
	}

    @Test
    public void download() {
        def action = new HttpBuilder()
        .path("http://it-ebooks-api.info/v1/search/java")
        .get()
        .create()

        printf 'code = %d\n',action.responseCode

        def parser = new JsonSlurper()
        def bookInfo = parser.parseText(action.getResponseText())


        def books = bookInfo['Books']

//        println books[0]

        printf 'Description = %s\n', books[0]['Description']
        printf 'isbn = %s\n'. books[0]['isbn']
        printf 'Image = %s\n', books[0]['Image']
        printf 'SubTitle = %s\n', books[0].SubTitle
        printf 'Title = $s\n', books[0].Title
    }
}
