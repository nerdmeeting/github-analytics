package org.springframework.github;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.contract.stubrunner.StubTrigger;
import org.springframework.cloud.contract.stubrunner.spring.AutoConfigureStubRunner;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.assertj.core.api.BDDAssertions.then;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = AnalyticsApplication.class)
@AutoConfigureStubRunner(ids = {"com.example.github:github-webhook"}, workOffline = true)
@ActiveProfiles("test")
public class AnalyticsApplicationTests {

	@Autowired StubTrigger stubTrigger;
	@Autowired GithubDataListener githubDataListener;

	@Before
	public void setup() {
		this.githubDataListener.clear();
	}

	@Test
	public void testWithV2StubData() {
		int initialSize = this.githubDataListener.stats.get();

		this.stubTrigger.trigger("issue_created_v2");

		then(this.githubDataListener.counter).isNotEmpty();
		then(this.githubDataListener.stats.get()).isGreaterThan(initialSize);
	}

}
