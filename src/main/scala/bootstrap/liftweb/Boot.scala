package bootstrap.liftweb

import java.sql.{Connection, DriverManager}

import _root_.net.liftweb._
import util._
import Helpers._

import common._
import http._
import sitemap._
import Loc._


/**
 * A class that's instantiated early and run.  It allows the application
 * to modify lift's environment
 */
class Boot {
  
  // Set up a logger to use for startup messages
  val logger = Logger(classOf[Boot])
  def boot {
    

    // where to search snippet
    LiftRules.addToPackages("code")

    // Build SiteMap

// taken from
// http://groups.google.com/group/liftweb/browse_thread/thread/971d4617bf6678b0/4e26757d92b881c8
    val contactViewByNickNameMenuItem
      = Menu.param[String]("viewContact", "View Contact",  (nickName : String) => {
                   //logger.debug("Nickname from URL: %s".format(nickName))
                   Box( (S.param("v") openOr("ERROR")))
                   },
                   version => version
                   ) / "agent-details-2"


    val entries = List(
      Menu.i("Home") / "index", 
      Menu.i("Agent Details") / "agent-details",

      // more complex because this menu allows anything in the
      // /static path to be visible
      Menu(Loc("Static", Link(List("static"), true, "/static/index"), 
	       "Help"), contactViewByNickNameMenuItem )) 

    // set the sitemap.  Note if you don't want access control for
    // each page, just comment this line out.
    LiftRules.setSiteMap(SiteMap(entries:_*))

    //Show the spinny image when an Ajax call starts
    LiftRules.ajaxStart =
      Full(() => LiftRules.jsArtifacts.show("ajax-loader").cmd)
    
    // Make the spinny image go away when it ends
    LiftRules.ajaxEnd =
      Full(() => LiftRules.jsArtifacts.hide("ajax-loader").cmd)

    // Force the request to be UTF-8
    LiftRules.early.append(_.setCharacterEncoding("UTF-8"))


    



  }
}



