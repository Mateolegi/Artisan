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
package io.github.mateolegi.Artisan.controllers;

import io.github.mateolegi.Artisan.util.Preferences;

/**
 *
 * @author mateo.leal
 */
public class Artisan {
    
    Preferences pref = new Preferences();
    
    String phpPath = getPHPPath();
    String composerPath = getComposerPath();

    final String NEWAPP             = composerPath + " create-project --prefer-dist laravel/laravel ";
    final String CUSTOMPORT         = " --port=";
    final String PHPVERSION         = phpPath + " -v";
    final String PHPMODULES         = phpPath + " -m";
    final String CLEARCOMPILED      = phpPath + " artisan clear-compiled ";
    final String ENV                = phpPath + " artisan env ";
    final String DOWN               = phpPath + " artisan down ";
    final String HELP               = phpPath + " artisan help ";
    final String INSPIRE            = phpPath + " artisan inspire ";
    final String MIGRATE            = phpPath + " artisan migrate ";
    final String OPTIMIZE           = phpPath + " artisan optimize ";
    final String SERVE              = phpPath + " artisan serve ";
    final String TINKER             = phpPath + " artisan tinker ";
    final String UP                 = phpPath + " artisan up ";
    final String APPNAME            = phpPath + " artisan app:name ";
    final String AUTHCLEARRESETS    = phpPath + " artisan auth:clear-resets ";
    final String CACHECLEAR         = phpPath + " artisan cache:clear ";
    final String CACHEFORGET        = phpPath + " artisan cache:forget ";
    final String CACHETABLE         = phpPath + " artisan cache:table ";
    final String CONFIGCACHE        = phpPath + " artisan config:cache ";
    final String CONFIGCLEAR        = phpPath + " artisan config:clear ";
    final String DBSEED             = phpPath + " artisan db:seed ";
    final String EVENTGENERATE      = phpPath + " artisan event:generate ";
    final String KEYGENERATE        = phpPath + " artisan key:generate ";
    final String MAKECONTROLLER     = phpPath + " artisan make:controller ";
    final String MAKEMODEL          = phpPath + " artisan make:model ";
    final String MAKEMIGRATION      = phpPath + " artisan make:migration ";
    final String MAKEAUTH           = phpPath + " artisan make:auth ";
    final String MAKEEVENT          = phpPath + " artisan make:event ";
    final String MAKEJOB            = phpPath + " artisan make:job ";
    final String MAKELISTENER       = phpPath + " artisan make:listener ";
    final String MAKEMAIL           = phpPath + " artisan make:mail ";
    final String MAKEMIDDLEWARE     = phpPath + " artisan make:middleware ";
    final String MAKENOTIFICATION   = phpPath + " artisan make:notification ";
    final String MAKEPOLICY         = phpPath + " artisan make:policy ";
    final String MAKEPROVIDER       = phpPath + " artisan make:provider ";
    final String MAKEREQUEST        = phpPath + " artisan make:request ";
    final String MAKESEEDER         = phpPath + " artisan make:seeder ";
    final String MAKETEST           = phpPath + " artisan make:test ";
    

    String getPHPPath(){
        String PHP = pref.getProp("configurations", "php-path");
        if (PHP.equals("")) {
            PHP = "php";
            pref.saveProp("configurations", "php-path", PHP);
        }
        return PHP;
    }
    
    String getComposerPath(){
        String composer = pref.getProp("configurations", "composer-path");
        if (composer.equals("")) {
            composer = "composer";
            pref.saveProp("configurations", "composer-path", composer);
        }
        return composer;
    }
    
}
