import com.salesforce.revoman.input.json.pojoToJson
import com.squareup.moshi.FromJson
import com.squareup.moshi.Moshi
import com.squareup.moshi.adapter
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import ga.overfullstack.contextdef.groot.MapperConfig
import ga.overfullstack.contextdef.groot.Match
import ga.overfullstack.contextdef.sobject.Udd
import ga.overfullstack.contextdef.state.ContextDefinition
import ga.overfullstack.contextdef.state.ContextMapping
import ga.overfullstack.contextdef.state.ContextNode
import ga.overfullstack.contextdef.state.ContextNodeMapping
import ga.overfullstack.contextdef.state.NodeAttribute
import ga.overfullstack.utils.readFileInResourcesToString
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

class ContextMappingGeneratorTest {

  @OptIn(ExperimentalStdlibApi::class)
  @Test
  fun `Context Def Validator`() {
    val contextDefAdapter = Moshi.Builder().build().adapter<ContextDefinition>()
    val contextDef =
      contextDefAdapter
        .fromJson(readFileInResourcesToString("context-def/context-def.json"))!!
        .payload
        .contextDefinition
        .contextDefinitionVersionList[0]
        .contextDefinitionVersion

    val salesTxnNode = contextDef.contextNodes[0]
    val salesTxnLevel0Attributes = salesTxnNode.nodeAttributes
    val nameToAttr = salesTxnLevel0Attributes.associateBy { it.name }

    val quoteEntitiesMapping = contextDef.contextMapping!![0]
    val quoteMapping = quoteEntitiesMapping.contextNodeMappings[0]
    val quoteAttributeMapping = quoteMapping.contextAttributeMappingList

    quoteAttributeMapping.forEach {
      nameToAttr[it.contextSObjectHydrationInfoList[0].queryAttribute]?.contextAttributeId shouldBe
        it.contextAttributeId
    }
  }

  @OptIn(ExperimentalStdlibApi::class)
  @Test
  fun `Context mapping generator`() {
    val contextDefAdapter =
      Moshi.Builder().addLast(KotlinJsonAdapterFactory()).build().adapter<ContextDefinition>()
    val contextDef =
      contextDefAdapter
        .fromJson(readFileInResourcesToString("context-def/groot/context-def.json"))!!
        .payload
        .contextDefinition
        .contextDefinitionVersionList[0]
        .contextDefinitionVersion

    val salesTxnNode = contextDef.contextNodes[0]
    val quoteMapperConfig =
      Moshi.Builder()
        .build()
        .adapter<MapperConfig>()
        .fromJson(readFileInResourcesToString("context-def/groot/quote-mapper-config.json"))!!
    val quoteFields = Udd.getFieldsForAPIName(quoteMapperConfig.sObjectName)!!

    val contextMapping =
      Moshi.Builder()
        .add(ContextMappingAdapter(quoteMapperConfig, quoteFields, salesTxnNode))
        .addLast(KotlinJsonAdapterFactory())
        .build()
        .adapter<ContextMapping>()
        .fromJson(
          readFileInResourcesToString("context-def/groot/context-node-mapping-template.json")
        )
    println(pojoToJson(ContextMapping::class.java, contextMapping!!))
  }

  class ContextMappingAdapter(
    private val mapperConfig: MapperConfig,
    private val fields: List<String>,
    private val node: ContextNode
  ) {
    @FromJson
    fun fromJson(contextNodeMapping: ContextNodeMapping): ContextMapping {
      val nameToAttrId =
        node.nodeAttributes.associateBy(NodeAttribute::name, NodeAttribute::contextAttributeId)
      val fieldMatch =
        mapperConfig.match.associateBy(Match::queryAttribute, Match::contextNodeAttributeName)
      val contextAttributeMapping = contextNodeMapping.contextAttributeMappingList[0]
      val contextSObjectHydrationInfo = contextAttributeMapping.contextSObjectHydrationInfoList[0]

      val contextAttrMappings =
        fields
          .filterNot { mapperConfig.excludeFields.contains(it) }
          .map { field ->
            contextAttributeMapping.copy(
              contextAttributeId = nameToAttrId[fieldMatch[field] ?: field] ?: "",
              contextSObjectHydrationInfoList =
                listOf(
                  contextSObjectHydrationInfo.copy(
                    queryAttribute = field,
                    sObjectDomain = mapperConfig.sObjectName
                  )
                )
            )
          }
      val contextNodeMappings =
        contextNodeMapping.copy(
          contextNodeId = node.contextNodeId,
          sObjectName = mapperConfig.sObjectName,
          contextAttributeMappingList = contextAttrMappings
        )

      return ContextMapping(mapperConfig.contextMappingName, listOf(contextNodeMappings))
    }
  }
}
