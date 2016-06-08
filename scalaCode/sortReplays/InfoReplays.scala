

package sortReplays

import scala.io.Source

class InfoReplays {
  def getReplayInfo(fullFileName:String):Array[String] = {
    val fireCast:Int = 0;
    val waterCast:Int = 1;
    val earthCast:Int = 2;
    val windCast:Int = 3;
    val odinCrip:Int = 4;
    val bahCrip:Int = 5;
    val levCrip:Int = 6;
    val phoenixCrip:Int = 7;
    
    var nums:Array[Int] = for(i <- (0 to 7).toArray) yield 0 
    val baseMap:Array[String] = Array[String](
      "Fire casts: ",
      "Water casts: ",
      "Earth casts: ",
      "Wind casts: ",
      "Odins summoned: ",
      "Bahamuts summoned: ",
      "Leviathans summoned: ",
      "Phoenixs summoned: "
    )
    
    val lines:List[String] = Source.fromFile(fullFileName).getLines.toList
    for(line <- lines) {
      val command:Array[String] = line.split('/')
      command(0) match {
        case "cast" => command(1) match {
          case "fire"=> nums(fireCast)+= 1
          case "water"=> nums(waterCast) += 1
          case "earth"=> nums(earthCast) += 1
          case "wind"=> nums(windCast) +=1
          case _ => ()
        }
        case "crip" => command(1) match {
          case "leviathan" => nums(levCrip) += 1
          case "bahamut" => nums(bahCrip) += 1
          case "odin" => nums(odinCrip) += 1
          case "phoenix" => nums(phoenixCrip) += 1
          case _ => ()
        }
        case _ => ()
      }
    }
    
    var index = -1
    for(line <- baseMap) yield {index += 1; line + nums(index) }
  }
}