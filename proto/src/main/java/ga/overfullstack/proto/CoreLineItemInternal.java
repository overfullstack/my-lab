package ga.overfullstack.proto;

import java.util.List;
import java.util.Map;

public class CoreLineItemInternal {
	private ExecuteConstraintsRequest executeConstraintsRequest;

	public CoreLineItemInternal() {
		executeConstraintsRequest = ExecuteConstraintsRequest.newBuilder().build();
	}

	// Getters and setters for org_id
	public String getOrgId() {
		return executeConstraintsRequest.getOrgId();
	}

	public void setOrgId(String orgId) {
		executeConstraintsRequest = executeConstraintsRequest.toBuilder().setOrgId(orgId).build();
	}

	// Getters and setters for context_ids
	public List<String> getContextIds() {
		return executeConstraintsRequest.getContextIdsList();
	}

	public void setContextIds(List<String> contextIds) {
		executeConstraintsRequest =
				executeConstraintsRequest.toBuilder()
						.clearContextIds()
						.addAllContextIds(contextIds)
						.build();
	}

	public void addContextId(String contextId) {
		executeConstraintsRequest =
				executeConstraintsRequest.toBuilder().addContextIds(contextId).build();
	}

	public int getContextIdsCount() {
		return executeConstraintsRequest.getContextIdsCount();
	}

	// Getters and setters for user_profile
	public String getUserProfile() {
		return executeConstraintsRequest.getUserProfile();
	}

	public void setUserProfile(String userProfile) {
		executeConstraintsRequest =
				executeConstraintsRequest.toBuilder().setUserProfile(userProfile).build();
	}

	// Getters and setters for root_line_items
	public List<CoreLineItem> getRootLineItems() {
		return executeConstraintsRequest.getRootLineItemsList();
	}

	public void setRootLineItems(List<CoreLineItem> rootLineItems) {
		executeConstraintsRequest =
				executeConstraintsRequest.toBuilder()
						.clearRootLineItems()
						.addAllRootLineItems(rootLineItems)
						.build();
	}

	public void addRootLineItem(CoreLineItem rootLineItem) {
		executeConstraintsRequest =
				executeConstraintsRequest.toBuilder().addRootLineItems(rootLineItem).build();
	}

	public int getRootLineItemsCount() {
		return executeConstraintsRequest.getRootLineItemsCount();
	}

	// Getters and setters for context_properties
	public Map<String, String> getContextProperties() {
		return executeConstraintsRequest.getContextPropertiesMap();
	}

	public void setContextProperties(Map<String, String> contextProperties) {
		executeConstraintsRequest =
				executeConstraintsRequest.toBuilder()
						.clearContextProperties()
						.putAllContextProperties(contextProperties)
						.build();
	}

	public String getContextProperty(String key) {
		return executeConstraintsRequest.getContextPropertiesOrDefault(key, "");
	}

	public void setContextProperty(String key, String value) {
		executeConstraintsRequest =
				executeConstraintsRequest.toBuilder().putContextProperties(key, value).build();
	}

	public boolean hasContextProperty(String key) {
		return executeConstraintsRequest.containsContextProperties(key);
	}

	public void removeContextProperty(String key) {
		executeConstraintsRequest =
				executeConstraintsRequest.toBuilder().removeContextProperties(key).build();
	}

	public int getContextPropertiesCount() {
		return executeConstraintsRequest.getContextPropertiesCount();
	}

	// Getter for the underlying ExecuteConstraintsRequest
	public ExecuteConstraintsRequest getExecuteConstraintsRequest() {
		return executeConstraintsRequest;
	}
}
