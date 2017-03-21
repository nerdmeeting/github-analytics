package org.springframework.github;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.contract.verifier.messaging.boot.AutoConfigureMessageVerifier;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import com.jayway.restassured.module.mockmvc.RestAssuredMockMvc;

import static org.mockito.BDDMockito.given;

/**
 * @author Marcin Grzejszczak
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = { AnalyticsApplication.class })
@AutoConfigureMessageVerifier
public class BaseClass {

	@Autowired GithubDataListener githubDataListener;

	@Before
	public void setup() {
		RestAssuredMockMvc.standaloneSetup(githubDataListener);

	}

	static class Config {
		@Bean IssuesRepository repository() {
			IssuesRepository repo = Mockito.mock(IssuesRepository.class);
			given(repo.count()).willReturn(5L);
			return repo;
		}
	}
}
