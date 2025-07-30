package ga.overfullstack.proto;

import static java.util.stream.Collectors.toMap;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.truth.Truth;
import com.google.protobuf.Any;
import com.google.protobuf.util.JsonFormat;
import ga.overfullstack.proto.AttributeDomainsProto2.AttributeDomainList2;
import ga.overfullstack.proto.AttributeDomainsProto2.AttributeDomains2;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import org.junit.jupiter.api.Test;

class AttributeDomains2Test {

	@Test
	void testCoreNearCore() throws Exception {
		final var beforeResponse =
				"""
						{
								"attributeDomains": {
										"attr1": [
												{
														"min": 1,
														"max": 1,
														"minIntValue": 1
												},
												{
														"min": 1,
														"max": 1,
														"minIntValue": 1
												}
										],
										"attr2": [
												{
														"min": 1,
														"max": 1,
														"minIntValue": 1
												}
										],
										"attr3": [
												"str1",
												"str2"
										]
								}
						}
						""";
		final var mapper = new ObjectMapper();
		Map<String, Object> map = mapper.readValue(beforeResponse, Map.class);
		final var attributeDomainsNCBefore = (Map<String, List<?>>) map.get("attributeDomains");

		// After we receive it in core, we process them like this
		final var attributeDomainsCoreBefore = coreTransformation(attributeDomainsNCBefore);

		// --- AFTER ---

		// Near-core: Build protobuf
		final var attributeDomainsNC =
				new LinkedHashMap<String, List<?>>() {
					{
						put(
								"attr1",
								List.of(
										new LinkedHashMap<String, Integer>() {
											{
												put("min", 1);
												put("max", 1);
												put("minIntValue", 1);
											}
										},
										new LinkedHashMap<String, Integer>() {
											{
												put("min", 1);
												put("max", 1);
												put("minIntValue", 1);
											}
										}));
						put(
								"attr2",
								List.of(
										new LinkedHashMap<String, Integer>() {
											{
												put("min", 1);
												put("max", 1);
												put("minIntValue", 1);
											}
										}));
						put("attr3", List.of("str1", "str2"));
					}
				};

		/*// NEAR-CORE: CoreLineItemInternal Setter `setAttributeDomains()`
		final var attributeDomainsTransformed =
				attributeDomainsNC.entrySet().stream()
						.collect(
								toMap(
										Entry::getKey,
										entry ->
												AttributeDomainList2.newBuilder()
														.addAllValues(entry.getValue().stream().map(v -> Any.pack))
														.build()));

		var nearCoreProtoResponse =
				AttributeDomains2.newBuilder().putAllAttributeDomains(attributeDomainsTransformed).build();

		// Serialize to JSON and send response to Core
		var nearCoreResponse = JsonFormat.printer().print(nearCoreProtoResponse);

		// ON CORE: deserialize the nearCoreResponse to Proto
		var parsedBuilder = AttributeDomains2.newBuilder();
		JsonFormat.parser().merge(nearCoreResponse, parsedBuilder);
		var attributeDomainsCore = parsedBuilder.build();

		// ON CORE: CoreLineItemInternal Getter
		final var attributeDomainsCoreAfter =
				attributeDomainsCore.getAttributeDomainsMap().entrySet().stream()
						.collect(
								toMap(
										Entry::getKey,
										entry ->
												entry.getValue().getValuesList().stream().map(String::valueOf).toList()));
		Truth.assertThat(attributeDomainsCoreAfter)
				.containsExactlyEntriesIn(attributeDomainsCoreBefore);*/
	}
  

	private static Map<String, List<String>> coreTransformation(
			Map<String, List<?>> attributeDomainsNCBefore) {
		return attributeDomainsNCBefore.entrySet().stream()
				.collect(
						toMap(Entry::getKey, entry -> entry.getValue().stream().map(String::valueOf).toList()));
	}
  
}
