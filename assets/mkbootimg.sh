#!/system/bin/sh
echo \#!/sbin/sh > /data/data/rs.pedjaapps.anthrax.flasher/files/createnewboot.sh

echo /data/data/rs.pedjaapps.anthrax.flasher/files/mkbootimg --kernel /data/data/rs.pedjaapps.anthrax.flasher/files/zImage --ramdisk /data/data/rs.pedjaapps.anthrax.flasher/files/boot.img-ramdisk.gz --cmdline \"$(cat /data/data/rs.pedjaapps.anthrax.flasher/files/boot.img-cmdline)\" --base $(cat /data/data/rs.pedjaapps.anthrax.flasher/files/boot.img-base) --output /data/data/rs.pedjaapps.anthrax.flasher/files/newboot.img >> /data/data/rs.pedjaapps.anthrax.flasher/files/createnewboot.sh
chmod 777 //data/data/rs.pedjaapps.anthrax.flasher/files/createnewboot.sh
/data/data/rs.pedjaapps.anthrax.flasher/files/createnewboot.sh
return $?
