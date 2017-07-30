/*
 * Artisan - GUI for Laravel developers.
 * Copyright (C) 2017  Mateo Leal.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package io.github.mateolegi.Artisan.util;

/**
 * Every command available on Artisan for laravel 5.4
 * @since 1.0
 * @author Mateo Leal
 */
public enum Artisan {
    CLEAR_COMPILED("clear-compiled"),
    DOWN("down"),
    ENV("env"),
    HELP("help"),
    INSPIRE("inspire"),
    LIST("list"),
    MIGRATE("migrate"),
    OPTIMIZE("optimize"),
    SERVE("serve"),
    TINKER("tinker"),
    UP("up"),
    //APP
    APP_NAME("app:name"),
    //AUTH
    AUTH_CLEAR_RESETS("auth:clear-resets"),
    //CACHE
    CACHE_CLEAR("cache:clear"),
    CACHE_FORGET("cache:forget"),
    CACHE_TABLE("cache:table"),
    //CONFIG
    CONFIG_CACHE("config:cache"),
    CONFIG_CLEAR("config:clear"),
    //DB
    DB_SEED("db:seed"),
    //EVENT
    EVENT_GENERATE("event:generate"),
    //KEY
    KEY_GENERATE("key:generate"),
    //MAKE
    MAKE_AUTH("make:auth"),
    MAKE_COMMAND("make:command"),
    MAKE_CONTROLLER("make:controller"),
    MAKE_EVENT("make:event"),
    MAKE_JOB("make:job"),
    MAKE_LISTENER("make:listener"),
    MAKE_MAIL("make:mail"),
    MAKE_MIDDLEWARE("make:middleware"),
    MAKE_MIGRATION("make:migration"),
    MAKE_MODEL("make:model"),
    MAKE_NOTIFICATION("make:notification"),
    MAKE_POLICY("make:policy"),
    MAKE_PROVIDER("make:provider"),
    MAKE_REQUEST("make:request"),
    MAKE_SEEDER("make:seeder"),
    MAKE_TEST("make:test"),
    //MIGRATE
    MIGRATE_INSTALL("migrate:install"),
    MIGRATE_REFRESH("migrate:refresh"),
    MIGRATE_RESET("migrate:reset"),
    MIGRATE_ROLLBACK("migrate:rollback"),
    MIGRATE_STATUS("migrate:status"),
    //NOTIFICATIONS
    NOTIFICATIONS_TABLE("notifications:table"),
    //QUEUE
    QUEUE_FAILED("queue:failed"),
    QUEUE_FAILED_TABLE("queue:failed-table"),
    QUEUE_FLUSH("queue:flush"),
    QUEUE_FORGET("queue:forget"),
    QUEUE_LISTEN("queue:listen"),
    QUEUE_RESTART("queue:restart"),
    QUEUE_RETRY("queue:retry"),
    QUEUE_TABLE("queue:table"),
    QUEUE_WORK("queue:work"),
    //ROUTE
    ROUTE_CACHE("route:cache"),
    ROUTE_CLEAR("route:clear"),
    ROUTE_LIST("route:list"),
    //SCHEDULE
    SCHEDULE_RUN("schedule:run"),
    //SESSION
    SESSION_TABLE("session:table"),
    //STORAGE
    STORAGE_LINK("storage:link"),
    //VENDOR
    VENDOR_PUBLISH("vendor:publish"),
    //VIEW
    VIEW_CLEAR("view:clear");
    
    private String value;
    
    Artisan(String value) {
        this.value = value;
    }
    
    public String getText() {
        return this.value;
    }
    
    public static Artisan fromString(String text) {
        for (Artisan b : Artisan.values()) {
            if (b.value.equalsIgnoreCase(text)) {
                return b;
            }
        }
        return null;
    }
}
