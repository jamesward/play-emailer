import org.specs2.mutable.Specification
import play.api.test.{FakeApplication, FakeRequest, WithApplication}
import play.api.test.Helpers._

class ApplicationSpec extends Specification {
  
  "Application.index" should {
    "render the index page" in new WithApplication {
      val result = controllers.Application.index()(FakeRequest())
  
      status(result) must equalTo(OK)
      contentType(result) must beSome("text/html")
      charset(result) must beSome("utf-8")
      contentAsString(result) must contain("Send Email")
    }
  }
  
  "Application.sendEmail" should {
    val application = FakeApplication(additionalConfiguration = Map("smtp.mock" -> true))
    "send an email" in new WithApplication(application) {
      val request = FakeRequest().withFormUrlEncodedBody(
        "email" -> application.configuration.getString("smtp.user").get,
        "subject" -> "Test",
        "message" -> "testing"
      )
      
      val result = controllers.Application.sendEmail()(request)
      status(result) must equalTo(OK)
      contentType(result) must beSome("text/html")
      charset(result) must beSome("utf-8")
      contentAsString(result) must contain("Email Sent!")
    }
  }
  
}
