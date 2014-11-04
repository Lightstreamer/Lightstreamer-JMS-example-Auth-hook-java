package jmsgw_auth_demo.hooks;

public enum AuthorizationResult {
    OK,
    TOKEN_INVALID,
    STOCKS_ACCESS_NOT_AUTHORIZED,
    PORTFOLIO_ACCESS_NOT_AUTHORIZED,
    CHAT_ACCESS_NOT_AUTHORIZED,
    UNKNOWN_DESTINATION
}
