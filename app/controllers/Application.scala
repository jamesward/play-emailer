package controllers

import play.api.mvc.{Action, Controller}
import play.api.Play.current
import com.typesafe.plugin._

object Application extends Controller {

  def index = Action {
    Ok(views.html.index())
  }
  
  def sendEmail = Action { request =>
    val form = request.body.asFormUrlEncoded.get
    val mail = use[MailerPlugin].email
    mail.setSubject(form("subject").head)
    mail.addRecipient(form("email").head)
    mail.addFrom(current.configuration.getString("smtp.user").get)
    mail.send(form("message").head)
    Ok(views.html.emailSent())
  }

}