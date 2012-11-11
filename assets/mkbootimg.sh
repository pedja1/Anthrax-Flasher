#!/system/bin/sh
echo \#!/system/bin/sh > /data/data/rs.pedjaapps.anthrax.flasher/files/createnewboot.sh


echo $1

echo /data/data/rs.pedjaapps.anthrax.flasher/files/mkbootimg --kernel /data/data/rs.pedjaapps.anthrax.flasher/files/temp/kernel/$1/zImage --ramdisk /data/data/rs.pedjaapps.anthrax.flasher/files/temp/boot.img-ramdisk.gz --cmdline \"$(cat /data/data/rs.pedjaapps.anthrax.flasher/files/temp/boot.img-cmdline)\" --base $(cat /data/data/rs.pedjaapps.anthrax.flasher/files/temp/boot.img-base) --output /data/data/rs.pedjaapps.anthrax.flasher/files/temp/newboot.img >> /data/data/rs.pedjaapps.anthrax.flasher/files/createnewboot.sh
chmod 777 //data/data/rs.pedjaapps.anthrax.flasher/files/createnewboot.sh
/data/data/rs.pedjaapps.anthrax.flasher/files/createnewboot.sh
return $?
