package ga.overfullstack.proto;

import ga.overfullstack.proto.AttributeDomainsProto.AttributeDomainList;
import ga.overfullstack.proto.AttributeDomainsProto.AttributeDomains;
import java.util.Map;

public class CoreLineItem {
	AttributeDomains attributeDomains;

	public CoreLineItem() {
		attributeDomains = AttributeDomains.newBuilder().build();
	}

	// Getter and setter for AttributeDomainList map

	/** Gets the complete map of AttributeDomainList entries */
	public Map<String, AttributeDomainList> getAttributeDomainListMap() {
		return attributeDomains.getAttributeDomainsMap();
	}

	/** Sets the complete map of AttributeDomainList entries */
	public void setAttributeDomainListMap(Map<String, AttributeDomainList> attributeDomainListMap) {
		attributeDomains =
				AttributeDomains.newBuilder().putAllAttributeDomains(attributeDomainListMap).build();
	}

	// Delegating getters and setters for AttributeDomainList

	/** Gets the AttributeDomainList for the specified key */
	public AttributeDomainList getAttributeDomainList(String key) {
		return attributeDomains.getAttributeDomainsOrDefault(
				key, AttributeDomainList.getDefaultInstance());
	}

	/** Sets the AttributeDomainList for the specified key */
	public void setAttributeDomainList(String key, AttributeDomainList value) {
		attributeDomains = attributeDomains.toBuilder().putAttributeDomains(key, value).build();
	}

	/** Checks if an AttributeDomainList exists for the specified key */
	public boolean hasAttributeDomainList(String key) {
		return attributeDomains.containsAttributeDomains(key);
	}

	/** Removes the AttributeDomainList for the specified key */
	public void removeAttributeDomainList(String key) {
		attributeDomains = attributeDomains.toBuilder().removeAttributeDomains(key).build();
	}

	/** Gets the count of AttributeDomainList entries */
	public int getAttributeDomainListCount() {
		return attributeDomains.getAttributeDomainsCount();
	}

	/** Clears all AttributeDomainList entries */
	public void clearAttributeDomainLists() {
		attributeDomains = AttributeDomains.newBuilder().build();
	}
}
