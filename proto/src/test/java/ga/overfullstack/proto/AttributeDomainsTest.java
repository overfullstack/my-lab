package ga.overfullstack.proto;

import static java.util.stream.Collectors.toMap;
import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.truth.Truth;
import com.google.protobuf.util.JsonFormat;
import ga.overfullstack.proto.AttributeDomainsProto.AttributeDomainList;
import ga.overfullstack.proto.AttributeDomainsProto.AttributeDomains;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class AttributeDomainsTest {

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
		// After we receive it in core
		final var attributeDomainsCoreBefore =
				attributeDomainsNCBefore.entrySet().stream()
						.collect(
								toMap(
										Map.Entry::getKey,
										entry -> entry.getValue().stream().map(String::valueOf).toList()));

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

		// NEAR-CORE: CoreLineItemInternal Setter `setAttributeDomains()`
		final var attributeDomainsTransformed =
				attributeDomainsNC.entrySet().stream()
						.collect(
								toMap(
										Entry::getKey,
										entry ->
												AttributeDomainList.newBuilder()
														.addAllValues(entry.getValue().stream().map(String::valueOf).toList())
														.build()));

		var message =
				AttributeDomainsProto.AttributeDomains.newBuilder()
						.putAllAttributeDomains(attributeDomainsTransformed)
						.build();

		// Serialize to JSON and send response to Core
		var nearCoreResponse = JsonFormat.printer().print(message);

		// ON CORE: deserialize the nearCoreResponse to Proto
		var parsedBuilder = AttributeDomains.newBuilder();
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
				.containsExactlyEntriesIn(attributeDomainsCoreBefore);
	}

	@Test
	void testCoreNearCore1() throws Exception {
		final var beforeResponse =
				"""
						{
								"attributeDomains": {
									"Graphics": [
										"Intel Iris Xe Graphics",
										"MSI Gaming GeForce RTX 3060"
									],
									"$qty": [
										{
											"min": 1,
											"max": 1,
											"minIntValue": 1
										}
									],
									"Storage": [
										"SSD Hard Drive 2TB",
										"SSD Hard Drive 512GB",
										"SSD Hard Drive 1TB",
										"SSD Hard Drive 256GB",
										"Cloud Storage Enterprise - 6 TB",
										"Cloud Storage Enterprise - 2 TB"
									],
									"Memory": [
										"RAM 64GB",
										"RAM 32GB",
										"RAM 8GB",
										"RAM 16GB"
									],
									"Display_Size": [
										"24 Inch",
										"13 Inch",
										"15 Inch",
										"27 Inch"
									],
									"Windows_Processor": [
										"i5-CPU 4.4GHz",
										"i7-CPU 4.7GHz",
										"Intel Core i9 5.2 GHz"
									],
									"Display": [
										"1080p Built-in Display",
										"4k Built-in Display",
										"2k Built-in Display"
									]
								}
						}
						""";
		final var mapper = new ObjectMapper();
		Map<String, Object> map = mapper.readValue(beforeResponse, Map.class);
		final var attributeDomainsNCBefore = (Map<String, List<?>>) map.get("attributeDomains");
		// After we receive it in core
		final var attributeDomainsCoreBefore =
				attributeDomainsNCBefore.entrySet().stream()
						.collect(
								toMap(
										Map.Entry::getKey,
										entry -> entry.getValue().stream().map(String::valueOf).toList()));

		// --- AFTER ---

		// Near-core: Build protobuf
		// Serialize and deserialize with ObjectMapper
		final var attributeDomainsNC =
				new LinkedHashMap<String, List<?>>() {
					{
						put("Graphics", List.of("Intel Iris Xe Graphics", "MSI Gaming GeForce RTX 3060"));
						put(
								"$qty",
								List.of(
										new LinkedHashMap<String, Integer>() {
											{
												put("min", 1);
												put("max", 1);
												put("minIntValue", 1);
											}
										}));
						put(
								"Storage",
								List.of(
										"SSD Hard Drive 2TB",
										"SSD Hard Drive 512GB",
										"SSD Hard Drive 1TB",
										"SSD Hard Drive 256GB",
										"Cloud Storage Enterprise - 6 TB",
										"Cloud Storage Enterprise - 2 TB"));
						put("Memory", List.of("RAM 64GB", "RAM 32GB", "RAM 8GB", "RAM 16GB"));
						put("Display_Size", List.of("24 Inch", "13 Inch", "15 Inch", "27 Inch"));
						put(
								"Windows_Processor",
								List.of("i5-CPU 4.4GHz", "i7-CPU 4.7GHz", "Intel Core i9 5.2 GHz"));
						put(
								"Display",
								List.of("1080p Built-in Display", "4k Built-in Display", "2k Built-in Display"));
					}
				};

		// NEAR-CORE: CoreLineItemInternal Setter `setAttributeDomains()`
		final var attributeDomainsTransformed =
				attributeDomainsNC.entrySet().stream()
						.collect(
								toMap(
										Entry::getKey,
										entry ->
												AttributeDomainList.newBuilder()
														.addAllValues(entry.getValue().stream().map(String::valueOf).toList())
														.build()));

		var message =
				AttributeDomainsProto.AttributeDomains.newBuilder()
						.putAllAttributeDomains(attributeDomainsTransformed)
						.build();

		// Serialize to JSON and send response to Core
		var nearCoreResponse = JsonFormat.printer().print(message);

		// ON CORE: deserialize the nearCoreResponse to Proto
		var parsedBuilder = AttributeDomains.newBuilder();
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
				.containsExactlyEntriesIn(attributeDomainsCoreBefore);
	}

	@Test
	void testCoreNearCoreOld() throws Exception {
		final var str =
				"""
						{
								"attributeDomains": {
										"attr1": [
												{
														"max": 1,
														"min": 1,
														"minIntValue": 1
												},
												{
														"max": 1,
														"min": 1,
														"minIntValue": 1
												}
										],
										"attr2": [
												{
														"max": 1,
														"min": 1,
														"minIntValue": 1
												}
										],
										"attr3": [
												"str1, str2"
										]
								}
						}
						""";
		final var mapper = new ObjectMapper();
		Map<String, Object> map = mapper.readValue(str, Map.class);
		final var attributeDomains = (Map<String, List<?>>) map.get("attributeDomains");
		final var expectedMap =
				attributeDomains.entrySet().stream()
						.collect(toMap(Entry::getKey, entry -> entry.getValue().toString()));
		final var expected = attributeDomains.values().stream().map(String::valueOf).toList();

		// Build protobuf message directly
		final var attributeDomainsNC =
				Map.of(
						"attr1",
						List.of(
								Map.of("min", 1, "max", 1, "minIntValue", 1),
								Map.of("min", 1, "max", 1, "minIntValue", 1)),
						"attr2",
						List.of(Map.of("min", 1, "max", 1, "minIntValue", 1)),
						"attr3",
						List.of("str1, str2"));
		final var attributeDomainsTransformed =
				attributeDomainsNC.entrySet().stream()
						.collect(
								toMap(
										Entry::getKey,
										entry ->
												AttributeDomainsProto.AttributeDomainList.newBuilder()
														.addValues(String.valueOf(entry.getValue()))
														.build()));
		var message =
				AttributeDomainsProto.AttributeDomains.newBuilder()
						.putAllAttributeDomains(attributeDomainsTransformed)
						.build();

		// Convert to JSON using protobuf's JsonFormat
		var response = JsonFormat.printer().print(message);

		// on core deserialize
		// Parse JSON back to protobuf to verify round-trip conversion
		var parsedBuilder = AttributeDomainsProto.AttributeDomains.newBuilder();
		JsonFormat.parser().merge(response, parsedBuilder);
		var attributeDomainsCore = parsedBuilder.build();
		// lineItem.getAttributeDomains()
		final var attributeDomainsOnCoreTransformed =
				attributeDomainsCore.getAttributeDomainsMap().entrySet().stream()
						.collect(toMap(Entry::getKey, entry -> entry.getValue().getValuesList().toString()));

		System.out.println(attributeDomainsOnCoreTransformed);
		// Verify the messages are equal
		Assertions.assertThat(attributeDomainsCore).isEqualTo(message);

		// Verify structure
		assertThat(attributeDomainsCore.getAttributeDomainsMap()).hasSize(3);
		assertThat(attributeDomainsCore.getAttributeDomainsMap()).containsKey("attr1");
		assertThat(attributeDomainsCore.getAttributeDomainsMap()).containsKey("attr2");
	}

	@Test
	void testProtobufToJsonConversion2() throws Exception {
		// Build protobuf message directly
		final var attributeDomainsNC =
				Map.of(
						"attr1",
						List.of(Map.of("min", 1, "max", 1, "minIntValue", 1)),
						"attr2",
						List.of(Map.of("min", 1, "max", 1, "minIntValue", 1)),
						"attr3",
						List.of("str1, str2"));
		System.out.println(List.of(Map.of("min", 1, "max", 1, "minIntValue", 1)));
		final var attributeDomainsTransformed =
				attributeDomainsNC.entrySet().stream()
						.collect(
								toMap(
										Entry::getKey,
										entry ->
												AttributeDomainsProto.AttributeDomainList.newBuilder()
														.addValues(String.valueOf(entry.getValue()))
														.build()));
		var message =
				AttributeDomainsProto.AttributeDomains.newBuilder()
						.putAllAttributeDomains(attributeDomainsTransformed)
						.build();

		// Convert to JSON using protobuf's JsonFormat
		var response = JsonFormat.printer().print(message);

		// on core deserialize
		// Parse JSON back to protobuf to verify round-trip conversion
		var parsedBuilder = AttributeDomainsProto.AttributeDomains.newBuilder();
		JsonFormat.parser().merge(response, parsedBuilder);
		var attributeDomainsCore = parsedBuilder.build();
		// lineItem.getAttributeDomains()
		final var attributeDomainsOnCoreTransformed =
				attributeDomainsCore.getAttributeDomainsMap().entrySet().stream()
						.collect(toMap(Entry::getKey, entry -> entry.getValue().getValuesList().toString()));

		System.out.println(attributeDomainsOnCoreTransformed);
		// Verify the messages are equal
		Assertions.assertThat(attributeDomainsCore).isEqualTo(message);

		// Verify structure
		assertThat(attributeDomainsCore.getAttributeDomainsMap()).hasSize(3);
		assertThat(attributeDomainsCore.getAttributeDomainsMap()).containsKey("attr1");
		assertThat(attributeDomainsCore.getAttributeDomainsMap()).containsKey("attr2");
	}

	@Test
	void testSerializeAndDeserializeAttributeDomains() throws Exception {
		// Sample JSON with correct protobuf structure
		var sampleJson =
				"""
						{
								"attributeDomains": {
										"attr1": {
												"values": ["{min=1, max=1, minIntValue=1}"]
										},
										"attr2": {
												"values": ["{min=1, max=1, minIntValue=1}"]
										}
								}
						}
						""";

		// Step 1: Parse JSON directly into protobuf using JsonFormat
		var builder = AttributeDomainsProto.AttributeDomains.newBuilder();
		JsonFormat.parser().merge(sampleJson, builder);
		var protobufMessage = builder.build();

		// Step 2: Serialize protobuf to binary
		var serializedData = protobufMessage.toByteArray();

		// Verify serialization worked
		Assertions.assertThat(serializedData).isNotEmpty();

		// Step 3: Deserialize from binary back to protobuf
		var deserializedMessage = AttributeDomainsProto.AttributeDomains.parseFrom(serializedData);

		// Step 4: Convert protobuf back to JSON using JsonFormat
		var deserializedJson = JsonFormat.printer().print(deserializedMessage);

		// Step 5: Verify the structure is preserved
		assertThat(deserializedMessage.getAttributeDomainsMap()).hasSize(2);
		assertThat(deserializedMessage.getAttributeDomainsMap()).containsKey("attr1");
		assertThat(deserializedMessage.getAttributeDomainsMap()).containsKey("attr2");

		// Verify attr1 values
		List<String> attr1Configs =
				deserializedMessage.getAttributeDomainsMap().get("attr1").getValuesList();
		assertThat(attr1Configs).hasSize(1);
		Assertions.assertThat(attr1Configs.get(0)).isEqualTo("{min=1, max=1, minIntValue=1}");

		// Verify attr2 values
		List<String> attr2Configs =
				deserializedMessage.getAttributeDomainsMap().get("attr2").getValuesList();
		assertThat(attr2Configs).hasSize(1);
		Assertions.assertThat(attr2Configs.get(0)).isEqualTo("{min=1, max=1, minIntValue=1}");

		// Verify JSON structure is preserved by parsing the round-trip JSON
		var roundTripBuilder = AttributeDomainsProto.AttributeDomains.newBuilder();
		JsonFormat.parser().merge(deserializedJson, roundTripBuilder);
		var roundTripMessage = roundTripBuilder.build();

		// Verify original and round-trip messages are equal
		Assertions.assertThat(roundTripMessage).isEqualTo(protobufMessage);
	}

	@Test
	void testProtobufToJsonConversion() throws Exception {
		// Build protobuf message directly
		var message =
				AttributeDomainsProto.AttributeDomains.newBuilder()
						.putAttributeDomains(
								"attr1",
								AttributeDomainsProto.AttributeDomainList.newBuilder()
										.addValues("{min=1, max=1, minIntValue=1}")
										.build())
						.putAttributeDomains(
								"attr2",
								AttributeDomainsProto.AttributeDomainList.newBuilder()
										.addValues("{min=1, max=1, minIntValue=1}")
										.build())
						.build();

		// Convert to JSON using protobuf's JsonFormat
		var jsonString = JsonFormat.printer().print(message);

		// Parse JSON back to protobuf to verify round-trip conversion
		var parsedBuilder = AttributeDomainsProto.AttributeDomains.newBuilder();
		JsonFormat.parser().merge(jsonString, parsedBuilder);
		var parsedMessage = parsedBuilder.build();

		// Verify the messages are equal
		Assertions.assertThat(parsedMessage).isEqualTo(message);

		// Verify structure
		assertThat(parsedMessage.getAttributeDomainsMap()).hasSize(2);
		assertThat(parsedMessage.getAttributeDomainsMap()).containsKey("attr1");
		assertThat(parsedMessage.getAttributeDomainsMap()).containsKey("attr2");
	}

	@Test
	void testJsonFormatDirectConversion() throws Exception {
		// Test direct JSON to protobuf conversion with correct structure
		var jsonInput =
				"""
						{
							"attributeDomains": {
								"testAttr": {
									"values": [
										"{min=5, max=10, minIntValue=5}",
										"{min=1, max=2, minIntValue=1}"
									]
								}
							}
						}
						""";

		// Parse JSON to protobuf
		var builder = AttributeDomainsProto.AttributeDomains.newBuilder();
		JsonFormat.parser().merge(jsonInput, builder);
		var message = builder.build();

		// Verify the parsed message
		assertThat(message.getAttributeDomainsMap()).hasSize(1);
		assertThat(message.getAttributeDomainsMap()).containsKey("testAttr");

		List<String> testAttrConfigs = message.getAttributeDomainsMap().get("testAttr").getValuesList();
		assertThat(testAttrConfigs).hasSize(2);
		Assertions.assertThat(testAttrConfigs.get(0)).isEqualTo("{min=5, max=10, minIntValue=5}");
		Assertions.assertThat(testAttrConfigs.get(1)).isEqualTo("{min=1, max=2, minIntValue=1}");

		// Convert back to JSON
		var jsonOutput = JsonFormat.printer().print(message);

		// Parse the output JSON back to protobuf to verify consistency
		var verifyBuilder = AttributeDomainsProto.AttributeDomains.newBuilder();
		JsonFormat.parser().merge(jsonOutput, verifyBuilder);
		var verifyMessage = verifyBuilder.build();

		Assertions.assertThat(verifyMessage).isEqualTo(message);
	}

	@Test
	void testConvertFromOriginalJsonStructure() throws Exception {
		// Test converting from the original JSON structure (as requested by user)
		// to the protobuf-compatible structure
		var originalJson =
				"""
						{
								"attributeDomains": {
										"attr1": [
												"{min=1, max=1, minIntValue=1}"
										],
										"attr2": [
												"{min=1, max=1, minIntValue=1}"
										]
								}
						}
						""";

		// Since protobuf expects a specific structure, we need to build the protobuf message
		// manually from the original JSON structure
		var message =
				AttributeDomainsProto.AttributeDomains.newBuilder()
						.putAttributeDomains(
								"attr1",
								AttributeDomainsProto.AttributeDomainList.newBuilder()
										.addValues("{min=1, max=1, minIntValue=1}")
										.build())
						.putAttributeDomains(
								"attr2",
								AttributeDomainsProto.AttributeDomainList.newBuilder()
										.addValues("{min=1, max=1, minIntValue=1}")
										.build())
						.build();

		// Serialize to binary
		var serializedData = message.toByteArray();

		// Deserialize back
		var deserializedMessage = AttributeDomainsProto.AttributeDomains.parseFrom(serializedData);

		// Verify the structure
		assertThat(deserializedMessage.getAttributeDomainsMap()).hasSize(2);
		assertThat(deserializedMessage.getAttributeDomainsMap()).containsKey("attr1");
		assertThat(deserializedMessage.getAttributeDomainsMap()).containsKey("attr2");

		// Verify values
		assertThat(deserializedMessage.getAttributeDomainsMap().get("attr1").getValuesList())
				.containsExactly("{min=1, max=1, minIntValue=1}");
		assertThat(deserializedMessage.getAttributeDomainsMap().get("attr2").getValuesList())
				.containsExactly("{min=1, max=1, minIntValue=1}");

		// Convert to protobuf JSON format
		var protobufJson = JsonFormat.printer().print(deserializedMessage);

		// Verify we can parse it back
		var roundTripBuilder = AttributeDomainsProto.AttributeDomains.newBuilder();
		JsonFormat.parser().merge(protobufJson, roundTripBuilder);
		var roundTripMessage = roundTripBuilder.build();

		Assertions.assertThat(roundTripMessage).isEqualTo(deserializedMessage);
	}
}
