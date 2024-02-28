package com.github.TreemanKing;

import de.myzelyam.supervanish.SuperVanishPlugin;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.metadata.MetadataValue;
import org.jetbrains.annotations.NotNull;
import java.util.ArrayList;
import java.util.List;

public class ListPlayersExpansion extends PlaceholderExpansion {

    private SuperVanishPlugin superVanishPlugin;

    @Override
    public @NotNull String getIdentifier() {
        return "listplayers";
    }

    @Override
    public @NotNull String getAuthor() {
        return "clip, TreemanKing";
    }

    @Override
    public @NotNull String getVersion() {
        return "1.1.0";
    }

    @Override
    public String onRequest(OfflinePlayer offlinePlayer, String params) {
        if (params.startsWith("with_perm_")) {

            String perm = params.replace("with_perm_", "");

            if (perm.isEmpty()) {
                return null;
            }

            List<String> listPlayers = new ArrayList<>();

            for (Player player : Bukkit.getOnlinePlayers()) {
                if (player.hasPermission(perm)) {
                    listPlayers.add(player.getName());
                }
            }

            return listToString(listPlayers);
        } else if (params.startsWith("in_world_")) {

            String world = params.replace("in_world_", "");

            if (world.isEmpty() || Bukkit.getWorld(world) == null) {
                return null;
            }

            List<String> listPlayers = new ArrayList<>();

            for (Player player : Bukkit.getWorld(world).getPlayers()) {
                listPlayers.add(player.getName());
            }

            return listToString(listPlayers);
        } else if (params.startsWith("not_vanished_in_world_")) {

            String world = params.replace("not_vanished_in_world_", "");

            if (world.isEmpty() || Bukkit.getWorld(world) == null) {
                return null;
            }

            List<String> listPlayers = new ArrayList<>();

            for (Player player : Bukkit.getWorld(world).getPlayers()) {
                if (!isVanished(player)) listPlayers.add(player.getName());
            }

            return listToString(listPlayers);
        }
        return null;
    }

    public static String listToString(List<String> list) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < list.size(); i++) {
            sb.append(list.get(i));
            if (i < list.size() - 1) {
                sb.append(", ");
            }
        }
        return sb.toString();
    }

    private boolean isVanished(Player player) {
        for (MetadataValue meta : player.getMetadata("vanished")) {
            if (meta.asBoolean()) return true;
        }
        return false;
    }
}
