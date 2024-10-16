package com.onboarding.crud.api.services;

import com.onboarding.crud.api.SmsSent;
import com.onboarding.crud.api.UserActionInfo;
import com.onboarding.crud.api.UserActionServiceGrpc;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

public class UserServiceGrpcClient {

	private final ManagedChannel managedChannel;
	private final UserActionServiceGrpc.UserActionServiceBlockingStub blockingStub;

	public UserServiceGrpcClient (String host, int port) {
		this.managedChannel = ManagedChannelBuilder.forAddress(host, port)
				.usePlaintext()
				.build();
		this.blockingStub = UserActionServiceGrpc.newBlockingStub(managedChannel);
	}

	public void shutdown () {
		managedChannel.shutdown();
	}

	public Boolean sendAction (String action, String userId) {
		UserActionInfo userActionInfo = UserActionInfo.newBuilder().
				setAction(action).setUserId(userId).build();
		SmsSent smsSent = blockingStub.sendAction(userActionInfo);
		return smsSent.getSmsOk();
	}
}
