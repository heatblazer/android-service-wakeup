#include "udpclient.h"
#include <iostream>

UdpClient::UdpClient(QObject *parent)
    : QObject (parent)
{
    pSocket = new QUdpSocket(this);
    pSocket->bind(QHostAddress::AnyIPv4, 12345);

    QObject::connect(pSocket, SIGNAL(readyRead()), this, SLOT(hData()));

}



 void  UdpClient::hData()
{
    QByteArray buffer;
    buffer.resize(pSocket->pendingDatagramSize());

    QHostAddress sender;
    quint16 senderPort;

       // qint64 QUdpSocket::readDatagram(char * data, qint64 maxSize,
       //                 QHostAddress * address = 0, quint16 * port = 0)
       // Receives a datagram no larger than maxSize bytes and stores it in data.
       // The sender's host address and port is stored in *address and *port
       // (unless the pointers are 0).

       pSocket->readDatagram(buffer.data(), buffer.size(),
                            &sender, &senderPort);

       std::cout << buffer.toStdString().c_str() << std::endl;

}
