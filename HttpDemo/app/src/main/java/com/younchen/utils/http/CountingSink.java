package com.younchen.utils.http;

import java.io.IOException;

import com.younchen.utils.http.callback.PrograssListner;

import okio.Buffer;
import okio.ForwardingSink;
import okio.Sink;

class CountingSink extends ForwardingSink {

	private long bytesWritten = 0;
	private long fileLenth;
	private PrograssListner listener;

	public CountingSink(Sink delegate, long fileLenth, PrograssListner listener) {
		super(delegate);
		this.fileLenth = fileLenth;
		this.listener = listener;
	}

	@Override
	public void write(Buffer source, long byteCount) throws IOException {
		super.write(source, byteCount);
		bytesWritten += byteCount;
		int prograss = (int) (bytesWritten * 100 / fileLenth);
		if (listener != null)
			listener.onPrograss(prograss);
	}
}