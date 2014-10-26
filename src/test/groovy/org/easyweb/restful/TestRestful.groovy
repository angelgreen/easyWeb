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
}
