#ifndef UDPCLIENT_H
#define UDPCLIENT_H
#include <QUdpSocket>
#include <QObject>

class UdpClient : public QObject
{
    Q_OBJECT
    QUdpSocket* pSocket;

private slots:
     void  hData();
public:
    explicit UdpClient(QObject* parent=0);
};

#endif // UDPCLIENT_H
