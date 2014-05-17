package kretes 
import com.excilys.ebi.gatling.core.Predef._
import com.excilys.ebi.gatling.http.Predef._
import com.excilys.ebi.gatling.jdbc.Predef._
import com.excilys.ebi.gatling.http.Headers.Names._
import akka.util.duration._
import bootstrap._
import assertions._
import akka.util.Duration
import java.util.concurrent.TimeUnit

class RecordedSimulation extends Simulation {

	val httpConf = httpConfig.baseURL("http://localhost:8080")
			.acceptHeader("text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8")
			.acceptEncodingHeader("gzip, deflate")
			.acceptLanguageHeader("en-US,en;q=0.5")
			.connection("keep-alive")
			.userAgentHeader("Mozilla/5.0 (X11; Ubuntu; Linux x86_64; rv:29.0) Gecko/20100101 Firefox/29.0")

	val scn = scenario("test my computer")
		.repeat(15)(exec(http("hello").get("/hello").check(status.is(200)).check(regex(".*Say hello to spray.*").exists) ).pause(
        Duration(10,TimeUnit.MILLISECONDS),Duration(100,TimeUnit.MILLISECONDS)))
    .repeat(2)(exec(http("request_2").get("/").check(status.is(404))))

	setUp(scn.users(1000).ramp(1).protocolConfig(httpConf))

}