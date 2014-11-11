package io.geobit.chain.providers.received;

import io.geobit.chain.providers.Prefix;

public interface ReceivedProvider  extends Prefix {
	public Long getReceived(String address);
}
