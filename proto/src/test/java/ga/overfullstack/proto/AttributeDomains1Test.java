package ga.overfullstack.proto;

import static com.google.common.truth.Truth.assertThat;
import static java.util.stream.Collectors.toMap;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.protobuf.util.JsonFormat;
import ga.overfullstack.proto.AttributeDomains1Proto.AttributeDomains1;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import org.junit.jupiter.api.Test;

class AttributeDomains1Test {

	@Test
	void testProtobufToJsonConversion3() throws Exception {
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
										"str1, str2"
								]
						}
				}
				""";
		final var mapper = new ObjectMapper();
		Map<String, Object> map = mapper.readValue(beforeResponse, Map.class);
		final var attributeDomainsBefore = (Map<String, List<?>>) map.get("attributeDomains");
    // After we receive it in core
		final var beforeAttributeDomains =
				attributeDomainsBefore.entrySet().stream()
						.collect(toMap(Map.Entry::getKey, entry -> entry.getValue().toString()));
  /*  attributeDomainsBefore.forEach((attrName, domain) -> {
      var attr = current.getAttributes().get(attrName);
      var attrRepr = attributeDefinitionsByDeveloperName.get(attrName);
      if (attr != null &&  attrRepr != null) {
        var attributePickList = attrRepr.getAttributePicklist();
        if (attributePickList != null) {
          var attrValuesReps = attributePickList.getValues();
          if (attrValuesReps != null) {
            final var domainStr = domain.stream().map(String::valueOf).collect(
                Collectors.toList());
            attrValuesReps.stream().filter(avr -> !domainStr.contains(avr.getTextValue())).forEach(avr -> addHideAttributePicklistValue(current, attrRepr.getId(), avr.getId()));
          }
        }
      }
    });*/
    
    // --- AFTER ---
    
		// Near-core: Build protobuf 
		final var attributeDomainsNC =
				new LinkedHashMap<String, List<?>>() {{
					put("attr1", List.of(
							new LinkedHashMap<String, Integer>() {{
								put("min", 1);
								put("max", 1);
								put("minIntValue", 1);
							}},
							new LinkedHashMap<String, Integer>() {{
								put("min", 1);
								put("max", 1);
								put("minIntValue", 1);
							}}
					));
					put("attr2", List.of(
							new LinkedHashMap<String, Integer>() {{
								put("min", 1);
								put("max", 1);
								put("minIntValue", 1);
							}}
					));
					put("attr3", List.of("str1, str2"));
				}};
		
    // NEAR-CORE: CoreLineItemInternal Setter `setAttributeDomains()`
    var message =
				AttributeDomains1Proto.AttributeDomains1.newBuilder()
						.putAllAttributeDomains(attributeDomainsNC.entrySet().stream()
                .collect(toMap(
                    Entry::getKey, entry -> entry.getValue().toString())))
						.build();

		// Serialize to JSON and send response to Core
		var nearCoreResponse = JsonFormat.printer().print(message);

    
		// on core deserialize the nearCoreResponse to Proto
		var parsedBuilder = AttributeDomains1.newBuilder();
		JsonFormat.parser().merge(nearCoreResponse, parsedBuilder);
		var attributeDomainsCore = parsedBuilder.build();
		
    // ON CORE: CoreLineItemInternal Getter
    final var attributeDomains = attributeDomainsCore.getAttributeDomainsMap();
    assertThat(attributeDomains).containsExactlyEntriesIn(beforeAttributeDomains);

/*    attributeDomains.forEach((attrName, domain) -> {
      var attr = current.getAttributes().get(attrName);
      var attrRepr = attributeDefinitionsByDeveloperName.get(attrName);
      if (attr != null &&  attrRepr != null) {
        var attributePickList = attrRepr.getAttributePicklist();
        if (attributePickList != null) {
          var attrValuesReps = attributePickList.getValues();
          if (attrValuesReps != null) {
            // final var domainStr = domain.stream().map(String::valueOf).collect(Collectors.toList());
            attrValuesReps.stream().filter(avr -> !domainStr.contains(avr.getTextValue())).forEach(avr -> addHideAttributePicklistValue(current, attrRepr.getId(), avr.getId()));
          }
        }
      }
    });*/
    
	}

  @Test
  void testProtobufToJsonConversion() throws Exception {
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
                    "str1, str2"
                ]
            }
        }
        """;
    final var mapper = new ObjectMapper();
    Map<String, Object> map = mapper.readValue(beforeResponse, Map.class);
    final var attributeDomains = (Map<String, List<?>>) map.get("attributeDomains");
    final var beforeAttributeDomains =
        attributeDomains.entrySet().stream()
            .collect(toMap(Map.Entry::getKey, entry -> entry.getValue().toString()));

    // --- AFTER ---

    // Near-core: Build protobuf 
    

    // Serialize to JSON and send response to Core
    var nearCoreResponse = """
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
                    "str1, str2"
                ]
            }
        }
        """;

    // on core deserialize the nearCoreResponse to Proto
    var parsedBuilder = AttributeDomains1.newBuilder();
    JsonFormat.parser().merge(nearCoreResponse, parsedBuilder);
    var attributeDomainsCore = parsedBuilder.build();

    // lineItem.getAttributeDomains()
    final var attributeDomainsMap = attributeDomainsCore.getAttributeDomainsMap();
    assertThat(attributeDomainsMap).containsExactlyEntriesIn(beforeAttributeDomains);
  }
  
}
