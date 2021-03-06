package com.walletconnect.walletconnectv2.clientsync.pairing

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import org.json.JSONObject
import com.walletconnect.walletconnectv2.ClientParams
import com.walletconnect.walletconnectv2.clientsync.pairing.after.payload.ProposalRequest
import com.walletconnect.walletconnectv2.clientsync.pairing.before.proposal.PairingProposedPermissions
import com.walletconnect.walletconnectv2.clientsync.pairing.before.proposal.PairingProposer
import com.walletconnect.walletconnectv2.clientsync.pairing.before.proposal.PairingSignal
import com.walletconnect.walletconnectv2.clientsync.pairing.before.success.PairingParticipant
import com.walletconnect.walletconnectv2.clientsync.pairing.before.success.PairingState
import com.walletconnect.walletconnectv2.clientsync.session.after.params.Reason
import com.walletconnect.walletconnectv2.common.Expiry
import com.walletconnect.walletconnectv2.common.Topic
import com.walletconnect.walletconnectv2.common.Ttl
import com.walletconnect.walletconnectv2.common.network.adapters.ExpiryAdapter
import com.walletconnect.walletconnectv2.common.network.adapters.JSONObjectAdapter
import com.walletconnect.walletconnectv2.common.network.adapters.TopicAdapter

sealed class Pairing : ClientParams {

    data class Proposal(
        val topic: Topic,
        val relay: JSONObject,
        val pairingProposer: PairingProposer,
        val pairingSignal: PairingSignal?,
        val permissions: PairingProposedPermissions?,
        val ttl: Ttl
    ) : Pairing()

    @JsonClass(generateAdapter = true)
    data class Success(
        @Json(name = "topic")
        @TopicAdapter.Qualifier
        val settledTopic: Topic,
        @Json(name = "relay")
        @JSONObjectAdapter.Qualifier
        val relay: JSONObject,
        @Json(name = "responder")
        val responder: PairingParticipant,
        @Json(name = "expiry")
        @ExpiryAdapter.Qualifier
        val expiry: Expiry,
        @Json(name = "state")
        val state: PairingState
    ) : Pairing()

    class Failure(val reason: String) : Pairing()

    @JsonClass(generateAdapter = true)
    data class PayloadParams(
        @Json(name = "request")
        val request: ProposalRequest
    ) : Pairing()

    @JsonClass(generateAdapter = true)
    class DeleteParams(
        @Json(name = "reason")
        val reason: Reason
    ) : Pairing()

    class PingParams : Pairing()

    data class NotificationParams(
        @Json(name = "type")
        val type: String,
        @Json(name = "data")
        val data: Any
    ) : Pairing()

    @JsonClass(generateAdapter = true)
    data class UpdateParams(
        @Json(name = "state")
        val state: PairingState
    ) : Pairing()
}