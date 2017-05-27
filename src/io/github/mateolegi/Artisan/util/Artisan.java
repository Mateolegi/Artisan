/*
 * The MIT License
 *
 * Copyright 2017 mateo.leal.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package io.github.mateolegi.Artisan.util;

/**
 *
 * @author mateo.leal
 */
public class Artisan {
    
    static Preferences pref = new Preferences();
    
    final static String PHPPATH = getPHPPath();
    final static String COMPOSERPATH = getComposerPath();

    public static final String NEWAPP             = COMPOSERPATH + " create-project --prefer-dist laravel/laravel ";
    public static final String CUSTOMPORT         = " --port=";
    public static final String PHPVERSION         = PHPPATH + " -v";
    public static final String PHPMODULES         = PHPPATH + " -m";
    public static final String CLEARCOMPILED      = PHPPATH + " artisan clear-compiled ";
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
    

    static String getPHPPath(){
        String PHP = pref.getProp("configurations", "php-path");
        if (PHP.equals("")) {
            PHP = "php";
            pref.saveProp("configurations", "php-path", PHP);
        }
        return PHP;
    }
    
    static String getComposerPath(){
        String composer = pref.getProp("configurations", "composer-path");
        if (composer.equals("")) {
            composer = "composer";
            pref.saveProp("configurations", "composer-path", composer);
        }
        return composer;
    }
    
}
