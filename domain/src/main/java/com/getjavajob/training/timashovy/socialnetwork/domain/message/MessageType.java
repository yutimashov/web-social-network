package com.getjavajob.training.timashovy.socialnetwork.domain.message;

/**
 * Types of Message that can exist in the application.
 * Each type has its own self-descriptive purpose.
 * ACCOUNT_PERSONAL is used for communication between accounts.
 * GROUP is used for managing messages on behalf of Group.
 * ACCOUNT_WALL is used for managing messages on Account personal page.
 */
public enum MessageType {

    ACCOUNT_PERSONAL, GROUP, ACCOUNT_WALL

}
