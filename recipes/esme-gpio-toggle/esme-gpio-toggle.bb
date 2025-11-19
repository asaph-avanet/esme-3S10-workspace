# Métadonnées de la recette
SUMMARY = "Application de bascule GPIO pour ESME-3S10"
HOMEPAGE = ""
SECTION = "base"

# Version de l'application
PV = "1.0"
PR = "r0"

# Licence (détectée à partir de COPYING.MIT dans le dossier source)
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://COPYING.MIT;md5=0195fcfcf70bcb21860c547352b12c7c"

# --- Dépendances et Héritage (Exigences du TP) ---

# On n'utilise PAS 'inherit make' pour éviter le conflit de parsing.
# On utilise 'pkgconfig' pour préparer l'environnement de cross-compilation.
inherit pkgconfig

DEPENDS = "libgpiod (< 2.0)"

SRC_URI = "git:////home/asaph/w/src/esme-3S10-gpio-toggle;protocol=file;branch=master"

# Indiquer à Yocto d'utiliser la dernière révision du dépôt (HEAD)
SRCREV = "${AUTOREV}"
S = "${WORKDIR}/git"
# --- Tâches Manuelles (do_compile / do_install) ---

# Tâche de Compilation : Lance 'make' dans le répertoire source (${S}).
do_compile() {
    oe_runmake
}

# Tâche d'Installation :
# Surcharge l'INSTALL_DIR de votre Makefile (${D} est le répertoire de destination Yocto).
do_install() {
    # 1. Installe le binaire (en surchargeant la destination dans le Makefile)
    oe_runmake install INSTALL_DIR=${D}
    
    # 2. Installe le script de démarrage (manuellement, car il ne fait pas partie du 'make install' standard)
    # L'installation se fait dans le dossier standard des services init.d
    install -d ${D}${sysconfdir}/init.d
    install -m 0755 ${S}/esme-gpio26-toggle ${D}${sysconfdir}/init.d/esme-gpio26-toggle
}
