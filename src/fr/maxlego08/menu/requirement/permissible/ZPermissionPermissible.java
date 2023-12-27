package fr.maxlego08.menu.requirement.permissible;

import fr.maxlego08.menu.api.button.Button;
import fr.maxlego08.menu.api.requirement.Action;
import fr.maxlego08.menu.api.requirement.permissible.PermissionPermissible;
import fr.maxlego08.menu.inventory.inventories.InventoryDefault;
import fr.maxlego08.menu.requirement.ZPermissible;
import fr.maxlego08.menu.zcore.logger.Logger;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

/**
 * Implementation of the {@link PermissionPermissible} interface that checks player permissions
 * based on a specific permission node and an optional reverse flag.
 */
public class ZPermissionPermissible extends ZPermissible implements PermissionPermissible {

    private final String permission;
    private final boolean isReverse;

    public ZPermissionPermissible(String permission, List<Action> denyActions, List<Action> successActions) {
        super(denyActions, successActions);
        String[] processedPermission = processPermission(permission);
        this.permission = processedPermission[0];
        this.isReverse = Boolean.parseBoolean(processedPermission[1]);
    }

    /**
     * Constructs a ZPermissionPermissible with the specified permission node.
     *
     * @param permission The permission node to check.
     */
    public ZPermissionPermissible(String permission) {
        super(new ArrayList<>(), new ArrayList<>());
        String[] processedPermission = processPermission(permission);
        this.permission = processedPermission[0];
        this.isReverse = Boolean.parseBoolean(processedPermission[1]);
    }

    private String[] processPermission(String permission) {
        boolean isReverse = permission != null && permission.startsWith("!");
        if (isReverse) permission = permission.substring(1);
        return new String[]{permission, String.valueOf(isReverse)};
    }

    /**
     * Checks whether the player has the necessary permission based on the specified permission node and reverse flag.
     *
     * @param player The player whose permission is being checked.
     * @return {@code true} if the player has the necessary permission, otherwise {@code false}.
     */
    @Override
    public boolean hasPermission(Player player, Button button, InventoryDefault inventory) {
        return this.isReverse != player.hasPermission(this.permission);
    }

    /**
     * Checks whether the ZPermissionPermissible instance is valid.
     *
     * @return {@code true} if the instance is valid, otherwise {@code false}.
     */
    @Override
    public boolean isValid() {
        if (this.permission == null) Logger.info("Permission is null !", Logger.LogType.WARNING);
        return this.permission != null;
    }

    /**
     * Gets the permission node associated with this permissible.
     *
     * @return The permission node.
     */
    @Override
    public String getPermission() {
        return this.permission;
    }

    /**
     * Checks whether the permission check should be reversed.
     *
     * @return {@code true} if the permission check should be reversed, otherwise {@code false}.
     */
    @Override
    public boolean isReverse() {
        return this.isReverse;
    }

}
