import com.sun.net.httpserver.HttpExchange;

                        BufferedReader reader = new BufferedReader(
                                new InputStreamReader(exchange.getRequestBody())
                        );

                        StringBuilder body = new StringBuilder();

                        String line;

                        while ((line = reader.readLine()) != null) {
                            body.append(line);
                        }

                        String requestBody = body.toString();

                        String name = requestBody.split("\"name\":\"")[1]
                                .split("\"")[0];

                        String email = requestBody.split("\"email\":\"")[1]
                                .split("\"")[0];

                        String designation = requestBody
                                .split("\"designation\":\"")[1]
                                .split("\"")[0];

                        PreparedStatement statement = connection.prepareStatement(
                                "INSERT INTO employees(name, email, designation) VALUES (?, ?, ?)"
                        );

                        statement.setString(1, name);
                        statement.setString(2, email);
                        statement.setString(3, designation);

                        statement.executeUpdate();

                        String response = "Employee Saved Successfully";

                        exchange.sendResponseHeaders(200, response.length());

                        OutputStream os = exchange.getResponseBody();

                        os.write(response.getBytes());

                        os.close();
                    }

                } catch (Exception e) {

                    try {

                        String response = e.getMessage();

                        exchange.sendResponseHeaders(500, response.length());

                        OutputStream os = exchange.getResponseBody();

                        os.write(response.getBytes());

                        os.close();

                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            }
        });

        server.start();

        System.out.println("Backend running on port 8080");
    }
}