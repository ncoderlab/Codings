/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package httpbotnet;

import java.io.PrintStream;
import java.util.Date;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

/**
 *
 * @author Student
 */
public class ConsoleRedirect extends PrintStream {
    private JTextArea console;
    
    public ConsoleRedirect(JTextArea console,PrintStream out) {
        super(out);
        this.console = console;
    }

    @Override
    public void print(String s) {
        super.print(s);
        this.console.append(new Date().getTime()+":"+s);
    }

    @Override
    public void print(Object obj) {
        super.print(obj);
        this.console.append(new Date().getTime()+":"+obj);
    }

    @Override
    public void println() {
        super.println();
        this.console.append(new Date().getTime()+":");
    }

    @Override
    public void println(String x) {
        super.println(x);
        this.console.append(new Date().getTime()+":"+x+"\r\n");
    }

    @Override
    public void println(Object x) {
        super.println(x);
        this.console.append(new Date().getTime()+":"+x+"\r\n");
    }

    @Override
    public PrintStream printf(String format, Object... args) {
        super.printf(format, args);
        this.console.append(new Date().getTime()+":"+String.format(format, args));
        return this;
    }
    
    
    
    
}
