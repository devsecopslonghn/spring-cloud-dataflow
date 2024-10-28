/*
 * Copyright 2017-2019 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.springframework.cloud.skipper.server.controller.docs;

import java.nio.charset.StandardCharsets;

import org.junit.jupiter.api.Test;

import org.springframework.cloud.skipper.domain.Release;
import org.springframework.cloud.skipper.domain.RollbackRequest;
import org.springframework.cloud.skipper.domain.StatusCode;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.util.StringUtils;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.subsectionWithPath;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
/**
 * @author Gunnar Hillert
 * @author Ilayaperumal Gopinathan
 * @author Corneil du Plessis
 */
class RollbackDocumentation extends BaseDocumentation {

	@Test
	void rollbackRelease() throws Exception {
		Release release = createTestRelease();
		when(this.skipperStateMachineService.rollbackRelease(any(RollbackRequest.class))).thenReturn(release);
		MvcResult result = this.mockMvc.perform(
				post("/api/release/rollback/{releaseName}/{releaseVersion}", release.getName(), release.getVersion())
				).andExpect(status().isCreated())
				.andDo(this.documentationHandler.document(
						pathParameters(parameterWithName("releaseName").description("The name of the release to rollback"),
								parameterWithName("releaseVersion").description("The release version to rollback")),
						responseFields(
								subsectionWithPath("_links").ignored(),
								fieldWithPath("name").description("Name of the release"),
								fieldWithPath("version").description("Version of the release"),
								fieldWithPath("info.status.statusCode").description(
										String.format("StatusCode of the release's status (%s)",
												StringUtils.arrayToCommaDelimitedString(StatusCode.values()))),
								fieldWithPath("info.status.platformStatus")
										.description("Status from the underlying platform"),
								fieldWithPath("info.firstDeployed").description("Date/Time of first deployment"),
								fieldWithPath("info.lastDeployed").description("Date/Time of last deployment"),
								fieldWithPath("info.deleted").description("Date/Time of when the release was deleted"),
								fieldWithPath("info.description")
										.description("Human-friendly 'log entry' about this release"),
								fieldWithPath("pkg.metadata.apiVersion")
										.description("The Package Index spec version this file is based on"),
								fieldWithPath("pkg.metadata.origin")
										.description("Indicates the origin of the repository (free form text)"),
								fieldWithPath("pkg.metadata.repositoryId")
										.description("The repository ID this Package belongs to."),
								fieldWithPath("pkg.metadata.repositoryName")
										.description("The repository name this Package belongs to."),
								fieldWithPath("pkg.metadata.kind")
										.description("What type of package system is being used"),
								fieldWithPath("pkg.metadata.name").description("The name of the package"),
								fieldWithPath("pkg.metadata.displayName").description("Display name of the release"),
								fieldWithPath("pkg.metadata.version").description("The version of the package"),
								fieldWithPath("pkg.metadata.packageSourceUrl")
										.description("Location to source code for this package"),
								fieldWithPath("pkg.metadata.packageHomeUrl")
										.description("The home page of the package"),
								fieldWithPath("pkg.metadata.tags")
										.description("A comma separated list of tags to use for searching"),
								fieldWithPath("pkg.metadata.maintainer").description("Who is maintaining this package"),
								fieldWithPath("pkg.metadata.description")
										.description("Brief description of the package"),
								fieldWithPath("pkg.metadata.sha256").description(
										"Hash of package binary that will be downloaded using SHA256 hash algorithm"),
								fieldWithPath("pkg.metadata.iconUrl").description("Url location of a icon"),
								fieldWithPath("pkg.templates[].name")
										.description("Name is the path-like name of the template"),
								fieldWithPath("pkg.templates[].data")
										.description("Data is the template as string data"),
								fieldWithPath("pkg.dependencies")
										.description("The packages that this package depends upon"),
								fieldWithPath("pkg.configValues.raw")
										.description("The raw YAML string of configuration values"),
								fieldWithPath("pkg.fileHolders")
										.description("Miscellaneous files in a package, e.g. README, LICENSE, etc."),
								fieldWithPath("configValues.raw")
										.description("The raw YAML string of configuration values"),
								fieldWithPath("manifest.data").description("The manifest of the release"),
								fieldWithPath("platformName").description("Platform name of the release"))))
				.andReturn();
	}

	@Test
	void rollbackReleaseRequest() throws Exception {
		Release release = createTestRelease();
		when(this.skipperStateMachineService.rollbackRelease(any(RollbackRequest.class))).thenReturn(release);

		final RollbackRequest rollbackRequest = new RollbackRequest(release.getName(), 1, 60000L);
		final MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(),
				MediaType.APPLICATION_JSON.getSubtype(), StandardCharsets.UTF_8);

		MvcResult result = this.mockMvc.perform(
				post("/api/release/rollback").accept(MediaType.APPLICATION_JSON).contentType(contentType)
						.content(convertObjectToJson(rollbackRequest)))
				.andExpect(status().isCreated())
				.andDo(this.documentationHandler.document(
						responseFields(
								subsectionWithPath("links").ignored(),
								fieldWithPath("name").description("Name of the release"),
								fieldWithPath("version").description("Version of the release"),
								fieldWithPath("info.status.statusCode").description(
										String.format("StatusCode of the release's status (%s)",
												StringUtils.arrayToCommaDelimitedString(StatusCode.values()))),
								fieldWithPath("info.status.platformStatus")
										.description("Status from the underlying platform"),
								fieldWithPath("info.firstDeployed").description("Date/Time of first deployment"),
								fieldWithPath("info.lastDeployed").description("Date/Time of last deployment"),
								fieldWithPath("info.deleted").description("Date/Time of when the release was deleted"),
								fieldWithPath("info.description")
										.description("Human-friendly 'log entry' about this release"),
								fieldWithPath("pkg.metadata.apiVersion")
										.description("The Package Index spec version this file is based on"),
								fieldWithPath("pkg.metadata.origin")
										.description("Indicates the origin of the repository (free form text)"),
								fieldWithPath("pkg.metadata.repositoryId")
										.description("The repository ID this Package belongs to."),
								fieldWithPath("pkg.metadata.repositoryName")
										.description("The repository name this Package belongs to."),
								fieldWithPath("pkg.metadata.kind")
										.description("What type of package system is being used"),
								fieldWithPath("pkg.metadata.name").description("The name of the package"),
								fieldWithPath("pkg.metadata.displayName").description("Display name of the release"),
								fieldWithPath("pkg.metadata.version").description("The version of the package"),
								fieldWithPath("pkg.metadata.packageSourceUrl")
										.description("Location to source code for this package"),
								fieldWithPath("pkg.metadata.packageHomeUrl")
										.description("The home page of the package"),
								fieldWithPath("pkg.metadata.tags")
										.description("A comma separated list of tags to use for searching"),
								fieldWithPath("pkg.metadata.maintainer").description("Who is maintaining this package"),
								fieldWithPath("pkg.metadata.description")
										.description("Brief description of the package"),
								fieldWithPath("pkg.metadata.sha256").description(
										"Hash of package binary that will be downloaded using SHA256 hash algorithm"),
								fieldWithPath("pkg.metadata.iconUrl").description("Url location of a icon"),
								fieldWithPath("pkg.templates[].name")
										.description("Name is the path-like name of the template"),
								fieldWithPath("pkg.templates[].data")
										.description("Data is the template as string data"),
								fieldWithPath("pkg.dependencies")
										.description("The packages that this package depends upon"),
								fieldWithPath("pkg.configValues.raw")
										.description("The raw YAML string of configuration values"),
								fieldWithPath("pkg.fileHolders")
										.description("Miscellaneous files in a package, e.g. README, LICENSE, etc."),
								fieldWithPath("configValues.raw")
										.description("The raw YAML string of configuration values"),
								fieldWithPath("manifest.data").description("The manifest of the release"),
								fieldWithPath("platformName").description("Platform name of the release"))))
				.andReturn();
	}
}
