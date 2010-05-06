package lmc;

// Copyright © 2010 Bart Massey <bart@cs.pdx.edu>
// Licensed under the "MIT License"
// Please see the file COPYING in this distribution

import java.io.*;
import java.net.*;

public class Client {
    BufferedReader in;
    PrintStream out;

    public String expect(String code, boolean verbose) 
      throws IOException {
	String response;
	while(true) {
	    response = in.readLine();
	    if (response == null)
		throw new IOException("expect: EOF");
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
	    if (code.equals(response.substring(0, 3)))
		return response.substring(4);
	    throw new IOException("expect: unexpected response");
	}
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
	String version = expect("100", false);
	if (!"imcs 2.3".equals(version))
	    throw new Error("client: imcs version mismatch");
	send("me " + username + " " + password, true);
	expect("201", true);
    }

    String getMove()
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

    void sendMove(String m) 
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

    public void offer(char color) 
      throws IOException {
	if (color == '?')
	    send("offer", true);
	else
	    send("offer " + color, true);
	expect("101", true);
	expect("102", true);
    }

    public void accept(String id, char color)
      throws IOException {
	if (color == '?')
	    send("accept " + id, true);
	else
	    send("accept " + id + " " + color, true);
	expect("103", true);
    }

    public void close() 
      throws IOException {
	in.close();
	out.close();
    }
}
