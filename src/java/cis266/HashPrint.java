
package cis266;

import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigInteger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 *
 * @author Joe Rae
 * going to use a servlet here because it's mostly Java code that I'll be writing. it's just simply getting the
 * data from the form and computing it here.  calling a doPost here would be the best way to me to do it
 * and the really weird thing is I wrote nearly the same program the other day when I was bored at work to print 
 * out the different hashes of a string so hopefully i remember what I did...
 */
public class HashPrint extends HttpServlet 
{


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException 
    {
        PrintWriter out = response.getWriter();
        response.setContentType("text/html");
        
        String userInput = request.getParameter("hashcalculate");
        int iterations = Integer.parseInt(request.getParameter("iterations"));
        String hashType = request.getParameter("algorithm");
        
         
        if (iterations <= 0)
        {
            // since there needs to be at least one iteration of the algorithm, setting the variable to at least 1
            iterations = 1;
        }
        
        try
        {
            String data = userInput;
            
            for (int i=1; i<=iterations; i++)
            {                
                MessageDigest md = MessageDigest.getInstance(hashType);
                md.reset();
                md.update(data.getBytes("UTF-8"));
                String hashedData=String.format("%040x", new BigInteger(1,md.digest()));
                data = hashedData;
                
            }
            
            out.println("<html><body><title>Hash Output</title>");
            out.println("<p>The text \"" + userInput + "\" hashed " + iterations + " time(s) using the "
                    + hashType + " algorithm is " + data + "</p>"); 
            out.println("<body></html>");
        }    
        
        catch (NoSuchAlgorithmException e)
        {
            out.println("There has been an algorithm error");
        }

        catch (Exception e)
        {
            out.println("There has been an error");
        }       
        
    }

}
