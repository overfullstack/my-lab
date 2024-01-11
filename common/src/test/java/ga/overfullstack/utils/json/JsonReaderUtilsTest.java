package com.salesforce.revoman.input.json;

import static ga.overfullstack.utils.json.JsonReaderUtilsKt.anyMap;
import static ga.overfullstack.utils.json.JsonReaderUtilsKt.list;
import static ga.overfullstack.utils.json.JsonReaderUtilsKt.nextBoolean;
import static ga.overfullstack.utils.json.JsonReaderUtilsKt.nextString;
import static ga.overfullstack.utils.json.JsonReaderUtilsKt.obj;
import static ga.overfullstack.utils.json.JsonReaderUtilsKt.skipValue;
import static ga.overfullstack.utils.json.JsonReaderUtilsTest.ConnectInputRepWithGraphUnMarshaller.of;

import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.JsonReader;
import com.squareup.moshi.JsonWriter;
import com.squareup.moshi.Moshi;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Supplier;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.revcloud.loki.common.adapters.EpochAdapter;

class JsonReaderUtilsTest {

  private static final String TEST_RESOURCES_PATH = "src/test/resources/";

  @Test
  @DisplayName("fromJson: PQ payload JSON --> PlaceQuoteInputRep")
  void sObjectReqToObjectInputRep() {
    final var pqUnmarshaller =
        of(
            PlaceQuoteInputRepresentation.class,
            PlaceQuoteInputRepresentation::new,
            (pqir, key, reader) -> {
              switch (key) {
                case "pricingPref" -> pqir.setPricingPref(
                    PricingPreferenceEnum.valueOf(nextString(reader)));
                case "doAsync" -> pqir.setDoAsync(nextBoolean(reader));
                default -> skipValue(reader);
              }
            });
    final var pqInputRep =
        JsonPojoUtils.<PlaceQuoteInputRepresentation>jsonFileToPojo(
            PlaceQuoteInputRepresentation.class,
            TEST_RESOURCES_PATH + "pq-payload.json",
            List.of(new EpochAdapter(), pqUnmarshaller));
    assertThat(pqInputRep).isNotNull();
    final var graph = pqInputRep.getGraph();
    assertThat(graph).isNotNull();
    assertThat(graph.records.recordsList).hasSize(6);
  }

  static class ConnectInputRepWithGraphUnMarshaller<T extends ConnectInputRepresentationWithGraph>
      extends JsonAdapter<T> {
    private final Supplier<T> inputRepSupplier;
    private final ParamBuilder<T> paramBuilder;

    private ConnectInputRepWithGraphUnMarshaller(
        Supplier<T> inputRepSupplier, ParamBuilder<T> paramBuilder) {
      this.inputRepSupplier = inputRepSupplier;
      this.paramBuilder = paramBuilder;
    }

    public static <T extends ConnectInputRepresentationWithGraph> Factory of(
        final Class<T> type, Supplier<T> inputRepSupplier, ParamBuilder<T> paramBuilder) {
      return new Factory() {
        @Override
        public @Nullable JsonAdapter<?> create(
            @NotNull Type requestedType,
            @NotNull Set<? extends Annotation> annotations,
            @NotNull Moshi moshi) {
          if (type != requestedType) {
            return null;
          }
          return new ConnectInputRepWithGraphUnMarshaller<>(inputRepSupplier, paramBuilder);
        }
      };
    }

    @Override
    public T fromJson(@NotNull JsonReader reader) {
      return obj(
          inputRepSupplier::get,
          reader,
          (pqir, key1) -> {
            if (key1.equals("graph")) {
              pqir.setGraph(
                  obj(
                      ObjectGraphInputRepresentation::new,
                      reader,
                      (ogi, key2) -> {
                        switch (key2) {
                          case "graphId" -> ogi.setGraphId(nextString(reader));
                          case "records" -> {
                            final var oripl = new ObjectWithReferenceInputRepresentationList();
                            ogi.setRecords(oripl);
                            oripl.setRecordsList(
                                list(
                                    ObjectWithReferenceInputRepresentation::new,
                                    reader,
                                    (orir, key3) -> {
                                      switch (key3) {
                                        case "referenceId" -> orir.setReferenceId(
                                            nextString(reader));
                                        case "record" -> {
                                          final var oirm = new ObjectInputRepresentationMap();
                                          orir.setRecord(oirm);
                                          oirm.setRecordBody(anyMap(reader));
                                        }
                                        default -> skipValue(reader);
                                      }
                                    }));
                          }
                          default -> skipValue(reader);
                        }
                      }));
            } else {
              paramBuilder.build(pqir, key1, reader);
            }
          });
    }

    @Override
    public void toJson(@NotNull JsonWriter writer, T ignore) {

      // noop
    }

    @FunctionalInterface
    public interface ParamBuilder<T extends ConnectInputRepresentationWithGraph> {
      void build(T inputRepWithGraph, String key, JsonReader reader);
    }
  }

  static class ConnectInputRepresentationWithGraph {
    private ObjectGraphInputRepresentation graph;
    private boolean isSetGraph;

    public ObjectGraphInputRepresentation getGraph() {
      return this.graph;
    }

    public void setGraph(ObjectGraphInputRepresentation graph) {
      this.graph = graph;
      this.isSetGraph = true;
    }

    public boolean _isSetGraph() {
      return this.isSetGraph;
    }
  }

  enum PricingPreferenceEnum {
    Force,
    Skip,
    System
  }

  static class PlaceQuoteInputRepresentation extends ConnectInputRepresentationWithGraph {

    private PricingPreferenceEnum pricingPref;

    private Boolean doAsync;

    private boolean isSetPricingPref;

    private boolean isSetDoAsync;

    public PricingPreferenceEnum getPricingPref() {
      return pricingPref;
    }

    public void setPricingPref(PricingPreferenceEnum pricingPref) {
      this.pricingPref = pricingPref;
      this.isSetPricingPref = true;
    }

    public void setDoAsync(Boolean doAsync) {
      this.doAsync = doAsync;
      this.isSetDoAsync = true;
    }

    public boolean _isSetPricingPref() {
      return this.isSetPricingPref;
    }
  }

  static class ObjectGraphInputRepresentation {

    private String graphId;

    private ObjectWithReferenceInputRepresentationList records;

    private boolean isSetGraphId;
    private boolean isSetRecords;

    public String getGraphId() {
      return this.graphId;
    }

    public void setGraphId(String graphId) {
      this.graphId = graphId;
      this.isSetGraphId = true;
    }

    public ObjectWithReferenceInputRepresentationList getRecords() {
      return this.records;
    }

    public void setRecords(ObjectWithReferenceInputRepresentationList records) {
      this.records = records;
      this.isSetRecords = true;
    }

    public boolean _isSetGraphId() {
      return this.isSetGraphId;
    }

    public boolean _isSetRecords() {
      return this.isSetRecords;
    }
  }

  static class ObjectWithReferenceInputRepresentationList {

    private List<ObjectWithReferenceInputRepresentation> recordsList;
    private boolean isSetRecordsList;

    public ObjectWithReferenceInputRepresentationList() {
      super();
      this.recordsList = new ArrayList<>();
    }

    public List<ObjectWithReferenceInputRepresentation> getRecordsList() {
      return this.recordsList;
    }

    public void setRecordsList(List<ObjectWithReferenceInputRepresentation> recordsList) {
      this.recordsList = recordsList;
      this.isSetRecordsList = true;
    }

    public boolean _isSetRecordsList() {
      return this.isSetRecordsList;
    }
  }

  static class ObjectWithReferenceInputRepresentation {

    private String referenceId;
    private ObjectInputRepresentationMap record;

    private boolean isSetReferenceId;
    private boolean isSetRecord;

    public ObjectInputRepresentationMap getRecord() {
      return this.record;
    }

    public void setRecord(ObjectInputRepresentationMap record) {
      this.record = record;
      this.isSetRecord = true;
    }

    public String getReferenceId() {
      return this.referenceId;
    }

    public void setReferenceId(String referenceId) {
      this.referenceId = referenceId;
      this.isSetReferenceId = true;
    }

    public boolean _isSetRecord() {
      return this.isSetRecord;
    }

    public boolean _isSetReferenceId() {
      return this.isSetReferenceId;
    }
  }

  static class ObjectInputRepresentationMap {

    private Map<String, Object> recordBody;
    private boolean isSetRecordBody;

    public ObjectInputRepresentationMap() {
      this.recordBody = new HashMap<>();
    }

    public Map<String, Object> getRecordBody() {
      return this.recordBody;
    }

    public void setRecordBody(Map<String, Object> recordBody) {
      this.recordBody = recordBody;
      this.isSetRecordBody = true;
    }

    public boolean _isSetRecordBody() {
      return this.isSetRecordBody;
    }
  }
}
