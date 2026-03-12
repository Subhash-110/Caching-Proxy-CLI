# Caching Proxy Server (CLI)

A **Command Line Interface (CLI)** tool that starts a caching proxy server.
The proxy forwards requests to an origin server, caches the responses, and returns cached responses for repeated requests.

This project demonstrates how caching works and how to build a proxy server that improves performance by reducing repeated requests to the origin server.

---

# Features

* Start a proxy server from the command line
* Forward requests to an origin server
* Cache responses for repeated requests
* Return cached responses when available
* Add headers indicating cache status
* Clear the cache using a CLI command

---

# How It Works

1. User starts the proxy server with a port and origin server.
2. The proxy listens for incoming requests.
3. When a request is received:

   * If the request exists in cache → return cached response.
   * If not cached → forward request to origin server.
4. Store the response in cache.
5. Add a header indicating cache status.

---

# Example Architecture

```
Client Request
      |
      v
Caching Proxy Server
      |
      |--- Cache HIT → Return Cached Response
      |
      |--- Cache MISS → Forward to Origin Server
                        |
                        v
                    Origin Server
```

---

# Example Origin Server

For testing, you can use:

DummyJSON API

Example base URL:

```
http://dummyjson.com
```

---

# Installation

Clone the repository:

```bash
git clone https://github.com/yourusername/caching-proxy.git
cd caching-proxy
```

Build the project:

```bash
mvn clean install
```

---

# Usage

Start the caching proxy server:

```bash
caching-proxy --port <number> --origin <url>
```

Example:

```bash
caching-proxy --port 3000 --origin http://dummyjson.com
```

The proxy server will run on:

```
http://localhost:3000
```

---

# Example Request

Request sent to proxy:

```
http://localhost:3000/products
```

Forwarded request:

```
http://dummyjson.com/products
```

---

# Cache Headers

The proxy adds headers indicating cache status.

If response is served from cache:

```
X-Cache: HIT
```

If response is fetched from origin server:

```
X-Cache: MISS
```

---

# Example Response Flow

First request:

```
GET http://localhost:3000/products
```

Response header:

```
X-Cache: MISS
```

Second request:

```
GET http://localhost:3000/products
```

Response header:

```
X-Cache: HIT
```

---

# Clearing the Cache

You can clear the cache using:

```bash
caching-proxy --clear-cache
```

This removes all stored cached responses.

---

# Technologies Used

* Java
* HTTP Server
* HTTP Client
* In-memory caching
* CLI argument parsing

---

# Future Improvements

* Add cache expiration (TTL)
* Add persistent cache storage
* Add logging
* Add request filtering
* Support multiple origin servers

---
