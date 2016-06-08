package sortReplays

import java.io.File
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths
import java.nio.file.attribute.BasicFileAttributeView
import java.nio.file.attribute.BasicFileAttributes
import java.nio.file.attribute.FileTime
import java.util.ArrayList
import scala.collection.JavaConversions._
import scenes.replayViewer.ReplayViewerController;

class SortReplays(val files:ArrayList[File]) {
  def sort(kindOfSort:String):Array[File] = {
    var buffer:Array[File] = new Array[File](files.length) 
    files.toArray(buffer)
    def swap(i:Int, j:Int) {
          val tmp:File = buffer(i)
          buffer(i) = buffer(j)
          buffer(j) = tmp
        }
    
    def quickSort(cmp:(File, File)=>Boolean, equal:(File,File)=>Boolean, low:Int, up:Int):Unit = {
        var i = low;
        var j = up;
        val forCmp = buffer((j - i)/2)
        do { //12534 52134
          while(cmp(buffer(i), forCmp)) i += 1
          while(!cmp(buffer(j), forCmp) && !equal(buffer(j), forCmp)) j -= 1
          if(i<=j) {
            swap(i,j)
            i+=1
            j-=1
          }
        }while(i<=j)
        if(j>0) quickSort(cmp, equal, low, j)
        if(up>i)quickSort(cmp, equal, low, up-i)
    }
    
    kindOfSort match {
      case "length" => quickSort(compareGameLength, equalLengths, 0, buffer.length - 1);buffer
      case "date" => quickSort(compareGameDates, equalDates, 0, buffer.length - 1);buffer
    }
  }
 
  
  
    
  def equalDates(file1:File, file2:File)= {
    def getFileCreationTime(file:File) = {
      val path:Path = Paths.get(file.getAbsolutePath)
      val basicView:BasicFileAttributeView = Files.getFileAttributeView(path, classOf[BasicFileAttributeView]);
      val basicAttr:BasicFileAttributes = basicView.readAttributes  
      basicAttr.creationTime
    }
    getFileCreationTime(file1).toMillis == getFileCreationTime(file2).toMillis
  }
  
  def equalLengths(file1:File, file2:File) = {
    val length1:Long = ReplayViewerController.getGameLength(file1)
    val length2:Long = ReplayViewerController.getGameLength(file2)
    length1 == length2
  }
  
  def compareGameLength(file1:File, file2:File) = {
    val length1:Long = ReplayViewerController.getGameLength(file1)
    val length2:Long = ReplayViewerController.getGameLength(file2)
    length1 > length2
  }
  def compareGameDates(file1:File, file2:File) = {
    def getFileCreationTime(file:File) = {
      val path:Path = Paths.get(file.getAbsolutePath)
      val basicView:BasicFileAttributeView = Files.getFileAttributeView(path, classOf[BasicFileAttributeView]);
      val basicAttr:BasicFileAttributes = basicView.readAttributes  
      basicAttr.creationTime
    }
    getFileCreationTime(file1).toMillis > getFileCreationTime(file2).toMillis    
  }
}