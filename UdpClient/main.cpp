#include <QCoreApplication>
#include <QUdpSocket>
#include <QByteArray>

#include "udpclient.h"


int main(int argc, char *argv[])
{
    QCoreApplication a(argc, argv);
    UdpClient client;

    return a.exec();
}
