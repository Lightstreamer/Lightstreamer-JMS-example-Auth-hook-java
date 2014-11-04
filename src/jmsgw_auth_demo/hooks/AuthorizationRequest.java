package jmsgw_auth_demo.hooks;

import java.util.Collections;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class AuthorizationRequest {
    
    // List of user-token pairs.
    // These infos are shared with the demo client.
    private static final ConcurrentHashMap<String, String> TOKENS= new ConcurrentHashMap<String,String>();
    static {
        TOKENS.put("user1","ikgdfigdfhihdsih");
        TOKENS.put("patient0","lookihaveanewtokenhere");
        TOKENS.put("leto","powerfultoken");
        TOKENS.put("gollum","toobadforyou");
        TOKENS.put("lucky","srsly");
    }
    
    // List of user-authorization paris.
    // These infos are shared with the demo client (the client simply shows these infos in the interface, does not directly use them)
    private static final ConcurrentHashMap<String, Set<String>> AUTHORIZATIONS= new ConcurrentHashMap<String,Set<String>>();
    static {
        
        // Authorizations for user "user1":
        Set<String> userSet= Collections.newSetFromMap(new ConcurrentHashMap<String, Boolean>());
        userSet.add("stocksTopic");
        userSet.add("chatTopic");
        
        AUTHORIZATIONS.put("user1", userSet);
        
        // Authorizations for user "patient0":
        // this user will never be able to connect
        
        // Authorizations for user "leto":
        userSet= Collections.newSetFromMap(new ConcurrentHashMap<String, Boolean>());
        userSet.add("stocksTopic");
        userSet.add("portfolioTopic");
        userSet.add("portfolioQueue");
        userSet.add("chatTopic");

        AUTHORIZATIONS.put("leto", userSet);
        
        // Authorizations for user "gollum":
        // no authorizations for this user
        userSet= Collections.newSetFromMap(new ConcurrentHashMap<String, Boolean>());
        
        AUTHORIZATIONS.put("gollum", userSet);
        
        // Authorizations for user "lucky":
        userSet= Collections.newSetFromMap(new ConcurrentHashMap<String, Boolean>());
        userSet.add("stocksTopic");
        
        AUTHORIZATIONS.put("lucky", userSet);
    }
    
    public static AuthorizationResult validateToken(String user, String token) {
        
        /*
         * In a real case, the application would lookup the user-token pair
         * on an external service (or a local cache); in this demo we simply 
         * lookup the hard-coded map. 
         */
        String correctToken= TOKENS.get(user);
        if ((correctToken != null) && correctToken.equals(token))
            return AuthorizationResult.OK;

        // Return an appropriate error
        return AuthorizationResult.TOKEN_INVALID;
    }
    
    public static AuthorizationResult authorizeDestination(String user, String destinationName) {
        
        /*
         * In a real case, the application would lookup the user authorizations
         * on an external service (or a local cache); in this demo we simply 
         * lookup the hard-coded map.
         */
        Set<String> authDestinations= AUTHORIZATIONS.get(user);
        if ((authDestinations != null) && authDestinations.contains(destinationName))
            return AuthorizationResult.OK;

        // Return an appropriate error
        if (destinationName.startsWith("stocks"))
            return AuthorizationResult.STOCKS_ACCESS_NOT_AUTHORIZED;
        else if (destinationName.startsWith("portfolio"))
            return AuthorizationResult.PORTFOLIO_ACCESS_NOT_AUTHORIZED;
        else if (destinationName.startsWith("chat"))
            return AuthorizationResult.CHAT_ACCESS_NOT_AUTHORIZED;
        else {
            
            // Here we allow unknown destination names 
            // as they may be temporary queues or topics
            return AuthorizationResult.OK;
        }
    }
    
    public static Map<String, AuthorizationResult> getUserAuthorizations(String user) {
        
        /*
         * In a real case, the application would lookup the user authorizations
         * on an external service (or a local cache); in this demo we simply 
         * preload a map with the possible authorization results from the 
         * hard-coded map.
         */
        Set<String> authDestinations= AUTHORIZATIONS.get(user);

        Map<String, AuthorizationResult> results= new ConcurrentHashMap<String, AuthorizationResult>();
        if (authDestinations != null) {
            results.put("stocksTopic", authDestinations.contains("stocksTopic") ? AuthorizationResult.OK : AuthorizationResult.STOCKS_ACCESS_NOT_AUTHORIZED);
            results.put("portfolioTopic", authDestinations.contains("portfolioTopic") ? AuthorizationResult.OK : AuthorizationResult.PORTFOLIO_ACCESS_NOT_AUTHORIZED);
            results.put("portfolioQueue", authDestinations.contains("portfolioQueue") ? AuthorizationResult.OK : AuthorizationResult.PORTFOLIO_ACCESS_NOT_AUTHORIZED);
            results.put("chatTopic", authDestinations.contains("chatTopic") ? AuthorizationResult.OK : AuthorizationResult.CHAT_ACCESS_NOT_AUTHORIZED);
        }

        return results;
    }
}
