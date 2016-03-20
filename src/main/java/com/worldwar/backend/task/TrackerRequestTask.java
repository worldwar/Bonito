package com.worldwar.backend.task;

import com.ning.http.client.*;
import com.worldwar.backend.TrackerRequest;
import com.worldwar.backend.TrackerResponse;
import com.worldwar.bencoding.BEncoding;
import com.worldwar.bencoding.BObject;
import com.worldwar.utility.Systems;

import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;

public class TrackerRequestTask implements Callable<Void> {
    private TrackerRequest request;
    private TrackerResponseCallback callable;

    public TrackerRequestTask(TrackerRequest request, TrackerResponseCallback callback) {
        this.request = request;
        this.callable = callback;
    }

    @Override
    public Void call() throws Exception {
        Request r = Systems.requestBuilder(this.request);
        AsyncHttpClient asyncHttpClient = new AsyncHttpClient();
        asyncHttpClient.executeRequest(r, new AsyncCompletionHandler<Response>() {
            @Override
            public Response onCompleted(Response response) throws Exception{
                String body = response.getResponseBody();
                BObject decode = BEncoding.decode(body);
                Map<String, Object> dict = (Map<String, Object>) BEncoding.javaObject(decode);
                TrackerResponse trackerResponse = new TrackerResponse();
                trackerResponse.setPeers((List)dict.get("peers"));
                trackerResponse.setInterval(Integer.valueOf(String.valueOf(dict.get("interval"))));
                callable.callback(trackerResponse);
                return response;
            }

            @Override
            public void onThrowable(Throwable t){
            }
        });
        return null;
    }
}
