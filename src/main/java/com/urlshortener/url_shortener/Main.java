package com.urlshortener.url_shortener;

import java.net.InetSocketAddress;

import com.sun.net.httpserver.HttpServer; // Import HttpServer class to create a simple HTTP server
import java.net.InetSocketAddress;

public class Main {

	public static void main(String[] args) throws Exception {
		// Create an HTTP server object that listens on port 8080
		// The second parameter (0) is backlog, which is maximum number of queued
		// incoming connections (0 means default)
		HttpServer server = HttpServer.create(new InetSocketAddress(8080), 0);

		// Register a context (endpoint) with path "/" handled by RootHandler class
		// This means when we visit http://localhost:8080, RootHandler will handle the

		server.createContext("/", new RootHandler());
		server.createContext("/shorten", new ShortenHandler());
		server.createContext("/register", new RegisterHandler());
		server.createContext("/login", new LoginHandler());
		server.createContext("/custom-shorten", new CustomShortenHandler());

		// Start the HTTP server so it begins listening for incoming requests
		server.start();

		// Print to console that server has started (for developer confirmation)
		System.out.println("Server started on port 8080");
	}
}
