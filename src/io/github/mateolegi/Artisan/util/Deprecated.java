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
 *
 * @author Mateo Leal
 */
public class Deprecated {
    
    static Preferences pref = new Preferences();
    
    final static String PHPPATH = getPHPPath();
    final static String COMPOSERPATH = getComposerPath();
    
    public static final String INSTALLLARAVEL[]   = {COMPOSERPATH, "global", "require", "\"laravel/installer\""};
    public static final String COMPOSERVERSION[]  = {PHPPATH, COMPOSERPATH, "-V"};
    public static final String NEWAPP             = PHPPATH + COMPOSERPATH + " create-project --prefer-dist laravel/laravel ";
    public static final String CUSTOMPORT         = " --port=";
    public static final String PHPVERSION[]       = {PHPPATH, "-v"};
    public static final String PHPMODULES[]       = {PHPPATH, "-m"};
    public static final String CLEARCOMPILED[]    = {PHPPATH, "artisan", "clear-compiled"};
    public static final String ENV                = PHPPATH + " artisan env ";
    public static final String DOWN               = PHPPATH + " artisan down ";
    public static final String HELP               = PHPPATH + " artisan help ";
    public static final String INSPIRE            = PHPPATH + " artisan inspire ";
    public static final String MIGRATE            = PHPPATH + " artisan migrate ";
    public static final String OPTIMIZE           = PHPPATH + " artisan optimize ";
    public static final String SERVE              = PHPPATH + " artisan serve ";
    public static final String TINKER             = PHPPATH + " artisan tinker ";
    public static final String UP                 = PHPPATH + " artisan up ";
    public static final String APPNAME            = PHPPATH + " artisan app:name ";
    public static final String AUTHCLEARRESETS    = PHPPATH + " artisan auth:clear-resets ";
    public static final String CACHECLEAR         = PHPPATH + " artisan cache:clear ";
    public static final String CACHEFORGET        = PHPPATH + " artisan cache:forget ";
    public static final String CACHETABLE         = PHPPATH + " artisan cache:table ";
    public static final String CONFIGCACHE        = PHPPATH + " artisan config:cache ";
    public static final String CONFIGCLEAR        = PHPPATH + " artisan config:clear ";
    public static final String DBSEED             = PHPPATH + " artisan db:seed ";
    public static final String EVENTGENERATE      = PHPPATH + " artisan event:generate ";
    public static final String KEYGENERATE        = PHPPATH + " artisan key:generate ";
    public static final String MAKECONTROLLER     = PHPPATH + " artisan make:controller ";
    public static final String MAKEMODEL          = PHPPATH + " artisan make:model ";
    public static final String MAKEMIGRATION      = PHPPATH + " artisan make:migration ";
    public static final String MAKEAUTH           = PHPPATH + " artisan make:auth ";
    public static final String MAKEEVENT          = PHPPATH + " artisan make:event ";
    public static final String MAKEJOB            = PHPPATH + " artisan make:job ";
    public static final String MAKELISTENER       = PHPPATH + " artisan make:listener ";
    public static final String MAKEMAIL           = PHPPATH + " artisan make:mail ";
    public static final String MAKEMIDDLEWARE     = PHPPATH + " artisan make:middleware ";
    public static final String MAKENOTIFICATION   = PHPPATH + " artisan make:notification ";
    public static final String MAKEPOLICY         = PHPPATH + " artisan make:policy ";
    public static final String MAKEPROVIDER       = PHPPATH + " artisan make:provider ";
    public static final String MAKEREQUEST        = PHPPATH + " artisan make:request ";
    public static final String MAKESEEDER         = PHPPATH + " artisan make:seeder ";
    public static final String MAKETEST           = PHPPATH + " artisan make:test ";
    
    public static boolean fileExists(){
        return pref.fileExists();
    }

    static String getPHPPath(){
        return pref.getProp("configurations", "php-path");
    }
    
    static String getComposerPath(){
        return pref.getProp("configurations", "composer-path");
    }
    
}
