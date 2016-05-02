package com.younchen.utils.http;

import java.io.IOException;

import okio.Buffer;
import okio.BufferedSink;
import okio.ForwardingSink;
import okio.Okio;
import okio.Sink;

import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.RequestBody;
import com.younchen.utils.http.callback.PrograssListner;

public class CustomRequestBody extends RequestBody {

	protected RequestBody delegate;
	protected PrograssListner listener;

	protected CountingSink countingSink;

	public CustomRequestBody(RequestBody delegate, PrograssListner listener) {
		this.delegate = delegate;
		this.listener = listener;
	}

	@Override
	public MediaType contentType() {
		return delegate.contentType();
	}

	@Override
	public long contentLength() throws IOException {
		return delegate.contentLength();
	}

	@Override
	public void writeTo(BufferedSink sink) throws IOException {
		BufferedSink bufferedSink;

		countingSink = new CountingSink(sink,contentLength(),listener);
		bufferedSink = Okio.buffer(countingSink);
		delegate.writeTo(bufferedSink);
		bufferedSink.flush();
	}



}
