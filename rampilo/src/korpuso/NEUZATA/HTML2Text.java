package korpuso.NEUZATA;


import java.io.*;

import javax.swing.text.*;
import javax.swing.text.html.*;

public class HTML2Text {
  public static void main(String[] args) throws Exception {
    //URL u = new URL("http://www.interkulturo.net/seminarioj/sarajevo/pri_sarajevo.php");
    for (int i=0; i<100; i++) {

    String html = "<html><head><title>Java Technology Forums</title></head><body>hejdas</body></html>";

    EditorKit kit = new HTMLEditorKit();
    Document doc = kit.createDefaultDocument();
// The Document class does not yet handle charset's properly.
    doc.putProperty("IgnoreCharsetDirective", Boolean.TRUE);
    try {
// Create a reader on the HTML content.
    Reader rd = new StringReader(html);
// Parse the HTML.
    kit.read(rd, doc, 0);
    System.out.println( doc.getText(0, doc.getLength()) );
    }
    catch (Exception e) {
    e.printStackTrace();
    }
  }
  }
}
