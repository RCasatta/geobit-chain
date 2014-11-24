/*
 * The MIT License (MIT)
 * 
 * Copyright (c) 2014 geobit.io 
 * 
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package io.geobit.chain.providers.addressunspents;

import static io.geobit.common.statics.Log.error;
import static io.geobit.common.statics.Log.log;
import io.geobit.common.entity.AddressTransactions;
import io.geobit.common.providers.AddressUnspentsProvider;

import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.SettableFuture;
public class AddressUnspentsFutureCallback implements FutureCallback<AddressTransactions> {
	private Long start;
	private SettableFuture<AddressTransactions> returned;
	private AddressUnspentsProvider  provider;
	private AddressUnspentsProviders receivedProviders;


	public AddressUnspentsFutureCallback(Long start, AddressUnspentsProvider provider
			, SettableFuture<AddressTransactions> returned, AddressUnspentsProviders receivedProviders) {
		super();
		this.start = start;
		this.returned = returned;
		this.provider = provider;
		this.receivedProviders = receivedProviders;
	}

	

	@Override
	public void onSuccess(AddressTransactions contents) {
		log("success from " + provider.getPrefix() + " val=" + contents);
		Long elabsed= System.currentTimeMillis() - start;   /* influenced by thread time but anyway it's for every thread */
				
		if(contents!=null) 
			receivedProviders.record(provider , elabsed );
		else
			receivedProviders.record(provider , null );
		
		returned.set(contents);
	}

	@Override
	public void onFailure(Throwable throwable) {
		error("failure from " + provider.getPrefix());
		receivedProviders.record(provider , null );
		returned.set(null);
	}

}
