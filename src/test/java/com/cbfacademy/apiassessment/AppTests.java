package com.cbfacademy.apiassessment;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.context.annotation.Description;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

import com.cbfacademy.apiassessment.model.Portfolio;

import java.net.URL;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(classes = PortfolioManagerApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
class AppTests {

	@LocalServerPort
	private int port;

	private URL base;

	@Autowired
	private TestRestTemplate restTemplate;

	@BeforeEach
	public void setUp() throws Exception {
		// set up the url before each test (avoid redundancy, consistent setup)
		this.base = new URL("http://localhost:" + port + "/api/v1/portfolios");
	}

	@Test
	@Description("/api/v1/portfolios endpoint returns all portfolios")
	public void getAllPortfoliosReturnsPortfoliosList() {
		// send HHTP GET request to the specified URL (expect the response to be
		// portfolio objects))
		ResponseEntity<Portfolio[]> response = restTemplate.getForEntity(base.toString(), Portfolio[].class);

		// expect this request to have http status code for ok
		assertEquals(200, response.getStatusCode().value());
		// expect at least 1 portfolio object
		assertTrue(Objects.requireNonNull(response.getBody()).length > 0);
	}

	@Test
	@Description("/api/v1/portfolios/{id} endpoint returns portfolio by ID")
	public void getPortfolioByIdReturnsPortfolio() {
		// sends HTTP get req, expecting response with List of portfolios
		ResponseEntity<List<Portfolio>> responseList = restTemplate.exchange(
				base.toString(),
				HttpMethod.GET,
				null,
				new ParameterizedTypeReference<>() {
				});

		// extract id of first portfolio in response
		UUID existingId = Objects.requireNonNull(responseList.getBody()).get(0).getPortfolioId();

		// send HHTP get req using the extracted id
		ResponseEntity<Portfolio> response = restTemplate.getForEntity(base.toString() + "/" + existingId,
				Portfolio.class);
		assertEquals(HttpStatus.OK, response.getStatusCode());

		// If the portfolio is found, check its attributes or structure as needed
		Portfolio portfolio = response.getBody();
		// checks a portfolio is retrieved and not null
		assertNotNull(portfolio);
		// compares retreived portfolio with the id extracted earlier
		assertEquals(existingId, portfolio.getPortfolioId());
	}

}
