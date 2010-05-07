package lmc;

// Copyright © 2010 Bart Massey <bart@cs.pdx.edu>
// Licensed under the "MIT License"
// Please see the file COPYING in this distribution

import java.io.*;
import java.net.*;

public class Client {
    BufferedReader in;
    PrintStream out;

    public String expectResponse(boolean verbose) 
      throws IOException {
	String response;
	while(true) {
	    response = in.readLine();
	    if (response == null)
		throw new IOException("expectResponse: EOF");
	    if (verbose)
		System.out.println(response);
	    if (response.length() < 3)
		continue;
	    int i = 0;
	    for (; i < 3; i++)
		if (!Character.isDigit(response.charAt(i)))
		    break;
	    if (i < 3)
		continue;
	    return response;
	}
    }

    public static String responseCode(String s) {
	return s.substring(0, 3);
    }

    public static String responseString(String s) {
	return s.substring(4);
    }

    public String expect(String code, boolean verbose) 
      throws IOException {
	String response = expectResponse(verbose);
	return responseString(response);
    }

    public void send(String s, boolean verbose) {
	if (verbose)
	    System.out.println(s);
	out.println(s);
    }

    Client(String server, String portStr,
	   String username, String password) throws IOException {
	int port = Integer.parseInt(portStr);
	Socket s = new Socket(server, port);
	InputStreamReader isr =
	    new InputStreamReader(s.getInputStream());
	in = new BufferedReader(isr);
	out = new PrintStream(s.getOutputStream(), true);
	String version = expectResponse(false);
	if (!"imcs 2.4".equals(responseString(version)))
	    throw new Error("client: imcs version mismatch");
	send("me " + username + " " + password, true);
	expect("201", true);
    }

    public String getMove()
      throws IOException {
	String line;
	char ch;
	while (true) {
	    line = in.readLine();
	    if (line == null)
		return null;
	    System.out.println(line);
	    if (line.length() == 0)
		continue;
	    ch = line.charAt(0);
	    if (ch == '!' || ch == '=')
		break;
	}
	if (ch == '=')
	    return null;
	return line.substring(2);
    }

    public void sendMove(String m) 
      throws IOException {
	String line;
	do {
	    line = in.readLine();
	    if (line == null)
		throw new IOException("server terminated unexpectedly");
	    System.out.println(line);
	} while (line.length() == 0 || line.charAt(0) != '?');
	System.out.println(m);
	out.println(m);
    }

    public char offer(char color) 
      throws IOException {
	if (color == '?')
	    send("offer", true);
	else
	    send("offer " + color, true);
	String code = responseCode(expectResponse(true));
	if (code.equals("107"))
	    color = 'W';
	else if (code.equals("108"))
	    color = 'B';
	else if (!code.equals("101"))
	    throw new IOException("offer: unknown response code");
	expect("102", true);
	return color;
    }

    public char accept(String id, char color)
      throws IOException {
	if (color == '?')
	    send("accept " + id, true);
	else
	    send("accept " + id + " " + color, true);
	String code = responseCode(expectResponse(true));
	if (code.equals("101"))
	    return '?';
	if (code.equals("105"))
	    return 'W';
	if (code.equals("106"))
	    return 'B';
	throw new IOException("accept: unknown response code");
    }

    public void close() 
      throws IOException {
	in.close();
	out.close();
    }
}
