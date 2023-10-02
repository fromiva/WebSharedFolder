# Network port configuration

The application uses network port 8080 by default, due to users have to
specify the port number in a URL:

```text
http://example.com:8080/
```

To configure port to 80 (default for HTTP) or 443 (default to HTTPS), one can use both
the configuration file or the command line argument. After that, users can access
without specifying a port number:

```text
http://example.com/
https://example.com/
```

## Use a configuration file

It is necessary to create `application.yaml` file in the application folder
and add the following content (example for port 80):

```yaml
server:
  port: 80
```

## Use a command line argument

When the application starts, it is possible to specify a port number as a command
line option, for example:

```text
java -jar wsf-0.0.1.jar --server.port=80
```

## Linux special notes

On a Linux operating system, it is necessary for user to have the root privileges
to use network ports up to 1024. So, if you want to start the application
as an average user on port 80 (for HTTP requests) or 443 (for HTTPS requests),
you are not able to do it directly. One of the solutions is to configure a NAT
(Network address translation) rule ro redirect network requests from one port to another.

Command to configure redirect from port 80 to 8080:

```shell
iptables -t nat -A PREROUTING -p tcp --dport 80 -j REDIRECT --to-ports 8080
```

Command to configure redirect from port 443 to 8443:

```shell
iptables -t nat -A PREROUTING -p tcp --dport 443 -j REDIRECT --to-ports 8443
```
