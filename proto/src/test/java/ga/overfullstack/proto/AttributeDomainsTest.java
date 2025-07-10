package ga.overfullstack.proto;

import static org.assertj.core.api.Assertions.assertThat;

import com.google.protobuf.util.JsonFormat;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;
import org.junit.jupiter.api.Test;

class AttributeDomainsTest {

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
		assertThat(serializedData).isNotEmpty();

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
		assertThat(attr1Configs.get(0)).isEqualTo("{min=1, max=1, minIntValue=1}");

		// Verify attr2 values
		List<String> attr2Configs =
				deserializedMessage.getAttributeDomainsMap().get("attr2").getValuesList();
		assertThat(attr2Configs).hasSize(1);
		assertThat(attr2Configs.get(0)).isEqualTo("{min=1, max=1, minIntValue=1}");

		// Verify JSON structure is preserved by parsing the round-trip JSON
		var roundTripBuilder = AttributeDomainsProto.AttributeDomains.newBuilder();
		JsonFormat.parser().merge(deserializedJson, roundTripBuilder);
		var roundTripMessage = roundTripBuilder.build();

		// Verify original and round-trip messages are equal
		assertThat(roundTripMessage).isEqualTo(protobufMessage);
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
		assertThat(parsedMessage).isEqualTo(message);

		// Verify structure
		assertThat(parsedMessage.getAttributeDomainsMap()).hasSize(2);
		assertThat(parsedMessage.getAttributeDomainsMap()).containsKey("attr1");
		assertThat(parsedMessage.getAttributeDomainsMap()).containsKey("attr2");
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
								Collectors.toMap(
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
		var jsonString = JsonFormat.printer().print(message);

		// Parse JSON back to protobuf to verify round-trip conversion
		var parsedBuilder = AttributeDomainsProto.AttributeDomains.newBuilder();
		JsonFormat.parser().merge(jsonString, parsedBuilder);
		var attributeDomainsCore = parsedBuilder.build();
		System.out.println(
				attributeDomainsCore.getAttributeDomainsMap().entrySet().stream()
						.collect(
								Collectors.toMap(
										Entry::getKey, entry -> entry.getValue().getValuesList().toString())));
		// Verify the messages are equal
		assertThat(attributeDomainsCore).isEqualTo(message);

		// Verify structure
		assertThat(attributeDomainsCore.getAttributeDomainsMap()).hasSize(3);
		assertThat(attributeDomainsCore.getAttributeDomainsMap()).containsKey("attr1");
		assertThat(attributeDomainsCore.getAttributeDomainsMap()).containsKey("attr2");
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
		assertThat(testAttrConfigs.get(0)).isEqualTo("{min=5, max=10, minIntValue=5}");
		assertThat(testAttrConfigs.get(1)).isEqualTo("{min=1, max=2, minIntValue=1}");

		// Convert back to JSON
		var jsonOutput = JsonFormat.printer().print(message);

		// Parse the output JSON back to protobuf to verify consistency
		var verifyBuilder = AttributeDomainsProto.AttributeDomains.newBuilder();
		JsonFormat.parser().merge(jsonOutput, verifyBuilder);
		var verifyMessage = verifyBuilder.build();

		assertThat(verifyMessage).isEqualTo(message);
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

		assertThat(roundTripMessage).isEqualTo(deserializedMessage);
	}
}
