package sortReplays

import scala.io.Source

class DecodeReplay {
  def decode(fullFileName:String):Array[String] = {
    for(line <- Source.fromFile(fullFileName).getLines.toArray) yield {
      decodeCast(line.split("/")) 
    }
  }
  
  def decodeCast(tokens:Array[String]):String = {
    def beg() = {
      tokens(1) match {
        case "fire" => "Player has casted fire"
        case "water" => "Player has casted water"
        case "earth" => "Player has casted earth"
        case "wind" => "Player has casted wind"
        case "Odin" => "The crip Odin has been summoned"
        case "Bahamut" => "The crip Bahamut has been summoned"
        case "Leviathan" => "The crip Leviathan has been summoned"
        case "Phoenix" => "The crip Phoenix has been summoned"
        case "exit" => "The game ended"
      }
    }
    beg + "at line " + tokens(2) + ", the time when action occured: " + tokens(3)
  }
}