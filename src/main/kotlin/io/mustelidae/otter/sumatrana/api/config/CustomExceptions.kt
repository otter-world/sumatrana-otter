package io.mustelidae.otter.sumatrana.api.config

open class CustomException(val error: io.mustelidae.otter.sumatrana.api.common.ErrorSource) : RuntimeException(error.message)

/**
 * Human Exception
 */
open class HumanException(error: io.mustelidae.otter.sumatrana.api.common.ErrorSource) : CustomException(error)
class DataNotFindException : HumanException {
    constructor(message: String) : super(
        io.mustelidae.otter.sumatrana.api.common.Error(
            io.mustelidae.otter.sumatrana.api.common.ErrorCode.HD00,
            message
        )
    )
    constructor(id: String, message: String) : super(
        io.mustelidae.otter.sumatrana.api.common.Error(
            io.mustelidae.otter.sumatrana.api.common.ErrorCode.HD00,
            message,
            mapOf("id" to id)
        )
    )
    constructor(id: Long, message: String) : this(id.toString(), message)
}

class PreconditionFailException(message: String) : HumanException(
    io.mustelidae.otter.sumatrana.api.common.Error(
        io.mustelidae.otter.sumatrana.api.common.ErrorCode.HD02,
        message
    )
)

// ignore in sentry
class DataNotSearchedException : HumanException {
    constructor(message: String) : super(
        io.mustelidae.otter.sumatrana.api.common.Error(
            io.mustelidae.otter.sumatrana.api.common.ErrorCode.HD01,
            message
        )
    )
    constructor(source: String, message: String) : super(
        io.mustelidae.otter.sumatrana.api.common.Error(
            io.mustelidae.otter.sumatrana.api.common.ErrorCode.HD01,
            message,
            mapOf("search source" to source)
        )
    )
    constructor(id: Long, message: String) : this(id.toString(), message)
}

class MissingRequestXHeaderException(headerName: String) : HumanException(
    io.mustelidae.otter.sumatrana.api.common.Error(
        io.mustelidae.otter.sumatrana.api.common.ErrorCode.HI02,
        "Missing request header $headerName"
    )
)

class InvalidArgumentException(message: String) : HumanException(
    io.mustelidae.otter.sumatrana.api.common.Error(
        io.mustelidae.otter.sumatrana.api.common.ErrorCode.HI01,
        message
    )
)

open class SystemException(error: io.mustelidae.otter.sumatrana.api.common.ErrorSource) : CustomException(error)
class DevelopMistakeException : SystemException {
    constructor(errorCode: io.mustelidae.otter.sumatrana.api.common.ErrorCode) : super(
        io.mustelidae.otter.sumatrana.api.common.Error(
            errorCode,
            errorCode.summary
        )
    )
    constructor(message: String, causeBy: Map<String, Any?>? = null) : super(
        io.mustelidae.otter.sumatrana.api.common.Error(
            io.mustelidae.otter.sumatrana.api.common.ErrorCode.PD01,
            message,
            causeBy
        )
    )
}

open class CommunicationException(error: io.mustelidae.otter.sumatrana.api.common.ErrorSource) : CustomException(error)
class ClientException(target: String, message: String, code: String? = null) : CommunicationException(
    io.mustelidae.otter.sumatrana.api.common.Error(
        io.mustelidae.otter.sumatrana.api.common.ErrorCode.C000,
        message,
        causeBy = mapOf(
            "target" to target,
            "clientErrorCode" to code
        )
    ).apply {
        refCode = code
    }
)
class ConnectionTimeoutException(target: String, timeoutConfig: Int, url: String) : CommunicationException(
    io.mustelidae.otter.sumatrana.api.common.Error(
        io.mustelidae.otter.sumatrana.api.common.ErrorCode.CT01,
        "$target Connection fail",
        causeBy = mapOf(
            "target" to target,
            "url" to url,
            "timeout" to timeoutConfig
        )
    )
)

class ReadTimeoutException(target: String, timeoutConfig: Int, url: String) : CommunicationException(
    io.mustelidae.otter.sumatrana.api.common.Error(
        io.mustelidae.otter.sumatrana.api.common.ErrorCode.CT02,
        "$target Data not Received",
        causeBy = mapOf(
            "target" to target,
            "url" to url,
            "timeout" to timeoutConfig
        )
    )
)

open class AsyncException(message: String, causeBy: Map<String, Any?>? = null) : CustomException(
    io.mustelidae.otter.sumatrana.api.common.Error(
        io.mustelidae.otter.sumatrana.api.common.ErrorCode.SA00,
        message, causeBy
    )
)

/**
 * Policy Exception
 */
open class PolicyException(error: io.mustelidae.otter.sumatrana.api.common.ErrorSource) : CustomException(error)

/**
 * UnAuthorized Exception
 */
open class UnAuthorizedException(error: io.mustelidae.otter.sumatrana.api.common.ErrorSource) : CustomException(error)

class PermissionException : UnAuthorizedException {
    constructor() : super(
        io.mustelidae.otter.sumatrana.api.common.Error(
            io.mustelidae.otter.sumatrana.api.common.ErrorCode.HA00,
            "Access denied"
        )
    )
    constructor(message: String) : super(
        io.mustelidae.otter.sumatrana.api.common.Error(
            io.mustelidae.otter.sumatrana.api.common.ErrorCode.HA01,
            message
        )
    )
}

class AccessDeniedException : UnAuthorizedException(
    io.mustelidae.otter.sumatrana.api.common.Error(
        io.mustelidae.otter.sumatrana.api.common.ErrorCode.HA00,
        "Unauthorized"
    )
)