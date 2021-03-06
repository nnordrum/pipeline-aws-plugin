/*
 * -
 * #%L
 * Pipeline: AWS Steps
 * %%
 * Copyright (C) 2017 Taimos GmbH
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */

package de.taimos.pipeline.aws;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class RoleSessionNameBuilderTest {
	@Test
	public void shortNamesAreNotStripped() throws Exception {
		String shortJobName = "shortName";
		String buildNumber = "1";
		final RoleSessionNameBuilder roleSessionNameBuilder = RoleSessionNameBuilder
				.withJobName(shortJobName)
				.withBuildNumber(buildNumber);
		final String result = roleSessionNameBuilder.build();
		assertEquals("roleSessionNameBuilder should not be strapped", "Jenkins-shortName-1", result);
	}

	@Test
	public void nameLongerThanAWSLimitAreStripped() throws Exception {
		String jobName = org.apache.commons.lang.StringUtils.repeat("s", 64);
		String buildNumber = "123";
		final RoleSessionNameBuilder roleSessionNameBuilder = RoleSessionNameBuilder.withJobName(jobName)
				.withBuildNumber(buildNumber);
		final String result = roleSessionNameBuilder.build();
		assertEquals("The result should be equal to the limit", 64, result.length());
	}

	@Test
	public void nameEqualToAWSLimitAreStripped() throws Exception {
		String jobName = org.apache.commons.lang.StringUtils.repeat("s", 52);
		String buildNumber = "123";
		final RoleSessionNameBuilder roleSessionNameBuilder = RoleSessionNameBuilder.withJobName(jobName)
				.withBuildNumber(buildNumber);
		final String result = roleSessionNameBuilder.build();
		assertEquals("The result should be equal to the limit", 64, result.length());
	}

}