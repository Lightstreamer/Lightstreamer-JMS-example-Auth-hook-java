package jmsgw_auth_demo.hooks;

public enum AuthorizationResult {
    
    // The string-ification of these enum names are used
    // as an error code, identified by the client to react
    // appropriately on the user interface
    OK,
    TOKEN_INVALID,
    STOCKS_ACCESS_NOT_AUTHORIZED,
    PORTFOLIO_ACCESS_NOT_AUTHORIZED,
    CHAT_ACCESS_NOT_AUTHORIZED
}
